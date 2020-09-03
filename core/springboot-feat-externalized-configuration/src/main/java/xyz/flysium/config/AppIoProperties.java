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

package xyz.flysium.config;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.validation.annotation.Validated;

/**
 * Converting Data Sizes Sample.
 *
 * @author Sven Augustus
 */
@ConfigurationProperties("application.io")
@Component
@Validated
public class AppIoProperties {

  @DataSizeUnit(DataUnit.KILOBYTES)
  private DataSize bufferSize;
  // = DataSize.ofKilobytes(2);

  @NotNull
  private DataSize sizeThreshold;
  //= DataSize.ofBytes(512);

  public DataSize getBufferSize() {
    return this.bufferSize;
  }

  public void setBufferSize(DataSize bufferSize) {
    this.bufferSize = bufferSize;
  }

  public DataSize getSizeThreshold() {
    return this.sizeThreshold;
  }

  public void setSizeThreshold(DataSize sizeThreshold) {
    this.sizeThreshold = sizeThreshold;
  }

}
