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

package xyz.flysium.config;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.flysium.constant.MyMQConstants;

/**
 * RabbitMQ Configuration. see more: <a>https://docs.spring.io/spring-amqp/docs/2.2.1.RELEASE/reference/html/</a>
 *
 * @author Sven Augustus
 */
@Configuration
public class RabbitMQConfig {

  @Bean
  public MessageConverter messageConverter() {
//    return new SimpleMessageConverter();
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ConditionalRejectingErrorHandler errorHandler() {
    return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
  }

  public static class MyFatalExceptionStrategy extends
      ConditionalRejectingErrorHandler.DefaultExceptionStrategy {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Override
    public boolean isFatal(Throwable t) {
      if (t instanceof ListenerExecutionFailedException) {
        ListenerExecutionFailedException lefe = (ListenerExecutionFailedException) t;
        logger.error("Failed to process inbound message from queue "
            + lefe.getFailedMessage().getMessageProperties().getConsumerQueue()
            + "; failed message: " + lefe.getFailedMessage(), t);
      }
      return super.isFatal(t);
    }

  }

  @Bean
  public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
    // 设置生产者 confirm 模式 channel.waitForConfirms() 后的处理逻辑
    rabbitTemplate.setConfirmCallback(new ConfirmCallback() {

      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
          System.out.println("Send OK.");
        } else {
          System.err.println(
              "Send Failed. cause= " + cause + ",correlation= " + correlationData.toString());
          // FIXME 注意这里需要添加处理消息重发的场景
        }
      }
    });
    // 设置生产者 confirm 模式 mandatory = true 参数启用时，如果交换器无法根据自身的类型和路由键找到一个符合条件的队列，则返回消息给生产者
    rabbitTemplate.setReturnCallback(new ReturnCallback() {
      @Override
      public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
          String routingKey) {
        System.out
            .printf("Return Messages, replyText= %s, exchange= %s, routingKey= %s , body= %s %n",
                replyText, exchange, routingKey, message);
        // FIXME 注意这里需要添加处理消息重发的场景
      }
    });
    return new RabbitMessagingTemplate(rabbitTemplate);
  }

  // 声明direct类型的Exchange
  @Bean
  public DirectExchange directExchange() {
    // 需要持久化消息，首先需要设置队列为持久化的
    return new DirectExchange(MyMQConstants.EXCHANGE_DIRECT, true, false);
  }

  // 创建一个 topic 类型的交换器
  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(MyMQConstants.EXCHANGE_TOPIC, true, false);
  }

  // 声明fanout类型的Exchange
  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange(MyMQConstants.EXCHANGE_FANOUT, true, false);
  }

  // 声明headers类型的Exchange
  @Bean
  public HeadersExchange headersExchange() {
    return new HeadersExchange(MyMQConstants.EXCHANGE_HEADER, true, false);
  }

  // 创建队列
  @Bean
  public Queue queue1() {
    return new Queue(MyMQConstants.QUEUE_NAME_1, true, false, false, new HashMap<>(8));
  }

  // 创建队列
  @Bean
  public Queue queue2() {
    Map<String, Object> arguments = new HashMap<String, Object>(8);
    // arguments.put("x-message-ttl", 6000);
    // FIXME 指定死信队列参数（x-dead-letter-exchange）
    // arguments.put("x-dead-letter-exchange", "samples.dlx.exchange");
    // FIXME 指定死信队列参数（x-dead-letter-routing-key） 指定路由键，如果没有特殊指定，则使用原队列的路由键
    // arguments.put("x-dead-letter-routing-key", "anything.now");
    return new Queue(MyMQConstants.QUEUE_NAME_2, true, false, false, arguments);
  }

  // 创建队列
  @Bean
  public Queue queue3() {
    return new Queue(MyMQConstants.QUEUE_NAME_3, true, false, false, new HashMap<>(8));
  }

  // 创建队列
  @Bean
  public Queue queue4() {
    return new Queue(MyMQConstants.QUEUE_NAME_4, true, false, false, new HashMap<>(8));
  }

  // 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
  @Bean
  public Binding binding(@Qualifier("queue1") Queue queue,
      DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(MyMQConstants.BINDING_KEY_D);
  }

  @Bean
  public Binding binding2(@Qualifier("queue2") Queue queue,
      TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(MyMQConstants.BINDING_KEY_T);
  }

}
