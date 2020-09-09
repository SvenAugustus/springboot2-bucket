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

package xyz.flysium.support.serializer;

import java.util.Map;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.ExtendedDeserializer;

/**
 * Kryo Deserializer.
 *
 * @author Sven Augustus
 */
public class KryoDeserializer<T> implements ExtendedDeserializer<T> {

  @Override
  public T deserialize(String topic, Headers headers, byte[] data) {
    return deserialize(topic, data);
  }

  @Override
  public T deserialize(String topic, byte[] data) {
    return KryoUtil.fromBytes(data);
  }

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    //TODO ，可以参考 JsonDeserializer
  }

  @Override
  public void close() {

  }

}
