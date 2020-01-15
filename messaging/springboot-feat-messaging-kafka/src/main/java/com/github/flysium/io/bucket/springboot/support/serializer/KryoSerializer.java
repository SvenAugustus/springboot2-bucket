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

package com.github.flysium.io.bucket.springboot.support.serializer;

import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.ExtendedSerializer;

/**
 * Kryo Serializer.
 *
 * @author Sven Augustus
 */
public class KryoSerializer<T> implements ExtendedSerializer<T> {

  @Override
  public byte[] serialize(String topic, Headers headers, T data) {
    return serialize(topic, data);
  }

  @Override
  public byte[] serialize(String topic, T data) {
    try {
      return KryoUtil.toBytes(data);
    } catch (Exception ex) {
      throw new SerializationException(
          "Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
    }
  }

  @Override
  public void configure(Map<String, ?> map, boolean b) {
    //TODO ，可以参考 JsonSerializer
  }

  @Override
  public void close() {

  }
}
