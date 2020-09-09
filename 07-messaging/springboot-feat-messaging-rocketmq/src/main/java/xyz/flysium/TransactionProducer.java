/*
 * Copyright 2020 SvenAugustus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.flysium;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import xyz.flysium.dao.entity.OrderInfo;
import xyz.flysium.dao.repository.IOrderService;
import xyz.flysium.support.rocketmq.RocketMQMessageConverter;

/**
 * Transaction Producer.
 *
 * @author Sven Augustus
 */
@Component
@EnableConfigurationProperties
public class TransactionProducer {

  private final Logger LOGGER = LoggerFactory.getLogger(getClass());

  private final RocketMQMessageConverter messageConverter;
  private final TransactionMQProducer producer;

  public TransactionProducer(RocketMQMessageConverter messageConverter,
      TransactionMQProducer producer, IOrderService orderService) {
    this.messageConverter = messageConverter;
    this.producer = producer;
    // FIXME 自己实现TransactionListener接口，并实现
    //  executeLocalTransaction 方法 (执行本地事务的，一般就是操作DB相关内容)和
    //  checkLocalTransaction方法 (用来提供给broker进行会查本地事务消息的，把本地事务执行的结果存储到redis或者DB中都可以，为回查做数据准备)。
    this.producer.setTransactionListener(new ChargeOrderTranListenerImpl(orderService));
  }

  /**
   * 示例：这里模拟：
   *
   * 用户访问第三方售票网关，进行购票操作。
   *
   * 用户发起下单操作（订单模块），( 通过 MQ 事务消息 发送到 支付模块 ) 执行支付转账操作
   */
  private class ChargeOrderTranListenerImpl implements TransactionListener {

    private final Logger LOGGER = LoggerFactory.getLogger(ChargeOrderTranListenerImpl.class);

    private final IOrderService orderService;
    public static final int CHECK_TIMES = 3;
    // FIXME 暂时使用内存存储，生产应该考虑使用 Redis， 或者干脆查询数据库不存在则视为 事务失败
    private final Map<Long, Long> checkTimes = new ConcurrentHashMap<>(100);

    private ChargeOrderTranListenerImpl(
        IOrderService orderService) {
      this.orderService = orderService;
    }

    // 执行本地订单入库操作
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
      // 消息解码
      OrderInfo orderInfo = (OrderInfo) arg;
      long orderId = orderInfo.getId();
      // 执行下单操作
      try {
        if (!orderService.insertOrder(orderInfo)) {
          LOGGER.error("订单入库失败,事务消息回滚,orderId={}", orderId);
          return LocalTransactionState.ROLLBACK_MESSAGE;
        }
      } catch (RuntimeException e) {
        LOGGER.error("订单入库异常,等待回查发起,orderId={},detailMessage={}", orderId, e.getMessage(), e);
        return LocalTransactionState.UNKNOW;
      }
      LOGGER.info("订单入库成功,orderId={}", orderId);
      return LocalTransactionState.COMMIT_MESSAGE;
    }

    // 提供回查
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
      // 消息解码
      OrderInfo orderInfo = (OrderInfo) messageConverter.fromMessage(msg);
      String msgId = msg.getMsgId();
      int reconsumeTimes = msg.getReconsumeTimes();

      long orderId = orderInfo.getId();
      Long times = checkTimes.getOrDefault(orderId, 1L);
      LOGGER.info("订单入库本地事务回查--接收到消息, msgId={},message={},reconsumeTimes={},回查次数={}",
          msgId, orderInfo, reconsumeTimes, times);

      // 订单查询
      OrderInfo orderInfoSaved = orderService.queryOrder(orderId);
      if (orderInfoSaved == null) {
        times++;
        checkTimes.put(orderId, times);
        if (times < CHECK_TIMES) {
          LOGGER.info("订单入库本地事务回查--本地不存在订单,[继续回查],orderId={},msgId={}", orderId, msgId);
          return LocalTransactionState.UNKNOW;
        }
        LOGGER.info("订单入库本地事务回查超过次数:{}--本地不存在订单,[消息回滚],orderId={},msgId={}", CHECK_TIMES,
            orderId, msgId);
        return LocalTransactionState.ROLLBACK_MESSAGE;
      }
      LOGGER.info("订单入库本地事务回查--本地存在订单信息,orderInfo={},msgId={},[消息提交]", orderInfoSaved, msgId);
      return LocalTransactionState.COMMIT_MESSAGE;
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  public void ready() {
    // Launch the instance.
    try {
      producer.start();
    } catch (MQClientException e) {
      throw new RuntimeException("启动[事务消息生产者]" + this.getClass().getName() + " 异常", e);
    }
    Runtime.getRuntime().addShutdownHook(new Thread(producer::shutdown));
    LOGGER.info("启动[事务消息生产者]" + this.getClass().getName() + " 成功");
  }

  /**
   * Send transactional message Synchronously.
   *
   * @param keys keys
   * @param object 我们建议的消息的大小应该不超过 512 K。
   */
  public TransactionSendResult sendMessageInTransaction(String keys, Object object)
      throws IOException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
    Message msg = messageConverter.toMessage(keys, object);
    TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, object);
    if (SendStatus.FLUSH_DISK_TIMEOUT.equals(sendResult.getSendStatus())
        || SendStatus.FLUSH_SLAVE_TIMEOUT.equals(sendResult.getSendStatus())) {
      //   重复或丢失
      //    如果您得到FLUSH_DISK_TIMEOUT、FLUSH_SLAVE_TIMEOUT并且 Broker 恰好在此时意外宕机，您会发现你的消息丢失。
      //    此时，您有两个选择，一个是不管它，这可能导致这个消息丢失；另一个是重新发送消息，这可能会导致消息重复。
      //    我们经常建议重新发送，然后再消费时使用某个方法移除重复的消息。除非你觉得一些信息丢失并不重要。
      //    但是请记住，当您得到 SLAVE_NOT_AVAILABLE 状态时，重新发送是没有用的。
      //    如果出现这种情况，您应该保存场景并通知集群管理员 （Cluster Manager）。
      System.err.println("Send Failed, status=" + sendResult.getSendStatus() + ", data=" + object);
    }
    return sendResult;
  }

}
