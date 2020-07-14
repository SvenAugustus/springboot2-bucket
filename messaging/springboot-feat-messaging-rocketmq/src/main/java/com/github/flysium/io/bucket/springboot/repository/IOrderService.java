/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.bucket.springboot.repository;

import com.github.flysium.io.bucket.springboot.entity.OrderInfo;

/**
 * Order Service.
 *
 * @author Sven Augustus
 */
public interface IOrderService {

  /**
   * Update Order.
   *
   * @param order Order
   * @return return true if it is Success.
   */
  boolean insertOrder(OrderInfo order);

  /**
   * Query Order by id.
   *
   * @param id order id
   * @return Order.
   */
  OrderInfo queryOrder(Long id);

}
