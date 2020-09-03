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

package xyz.flysium.dao.repository;

import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.flysium.dao.entity.OrderInfo;

/**
 * Order Service.
 *
 * @author Sven Augustus
 */
@Service
public class OrderServiceImpl implements IOrderService {

  private final OrderRepository orderRepository;

  public OrderServiceImpl(
      OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public boolean insertOrder(OrderInfo order) {
    if (order.getId() == null || queryOrder(order.getId()) != null) {
      return false;
    }
    /**
     * FIXME 模拟事务消息的本地执行情况
     */
    if (Objects.equals("msg-13", order.getState())) {
      throw new IllegalArgumentException("非法参数");
    }

    this.orderRepository.save(order);
    return true;
  }

  @Override
  public OrderInfo queryOrder(Long id) {
    return orderRepository.findById(id).orElse(null);
  }
}
