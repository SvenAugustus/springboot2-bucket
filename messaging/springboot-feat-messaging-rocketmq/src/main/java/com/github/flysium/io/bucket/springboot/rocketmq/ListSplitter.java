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

package com.github.flysium.io.bucket.springboot.rocketmq;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.rocketmq.common.message.Message;

/**
 * List Splitter.
 *
 * @author Sven Augustus
 */
public class ListSplitter implements Iterator<List<Message>> {

  public final int SIZE_LIMIT = 1000 * 1000;
  private final List<Message> messages;
  private int currIndex;

  public ListSplitter(List<Message> messages) {
    this.messages = messages;
  }

  @Override
  public boolean hasNext() {
    return currIndex < messages.size();
  }

  @Override
  public List<Message> next() {
    int nextIndex = currIndex;
    int totalSize = 0;
    for (; nextIndex < messages.size(); nextIndex++) {
      Message message = messages.get(nextIndex);
      int tmpSize = message.getTopic().length() + message.getBody().length;
      Map<String, String> properties = message.getProperties();
      for (Map.Entry<String, String> entry : properties.entrySet()) {
        tmpSize += entry.getKey().length() + entry.getValue().length();
      }
      //for log overhead
      tmpSize = tmpSize + 20;
      if (tmpSize > SIZE_LIMIT) {
        //it is unexpected that single message exceeds the SIZE_LIMIT
        //here just let it go, otherwise it will block the splitting process
        if (nextIndex - currIndex == 0) {
          //if the next sublist has no element, add this one and then break, otherwise just break
          nextIndex++;
        }
        break;
      }
      if (tmpSize + totalSize > SIZE_LIMIT) {
        break;
      } else {
        totalSize += tmpSize;
      }

    }
    List<Message> subList = messages.subList(currIndex, nextIndex);
    currIndex = nextIndex;
    return subList;
  }
}