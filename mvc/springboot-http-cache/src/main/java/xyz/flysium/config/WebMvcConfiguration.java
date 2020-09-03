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

import java.util.concurrent.TimeUnit;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sven Augustus
 * @version 1.0
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("*.js")
        // see org.springframework.boot.autoconfigure.web.ResourceProperties
        .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/")
        .setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).cachePublic());
  }

  /**
   * 使用 ETag
   */
  @Bean
  public FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean() {
    ShallowEtagHeaderFilter eTagFilter = new ShallowEtagHeaderFilter();
    // 设置为 weakETag，默认为 false
    eTagFilter.setWriteWeakETag(false);
    FilterRegistrationBean<ShallowEtagHeaderFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(eTagFilter);
    registration.addUrlPatterns("*.js");
    return registration;
  }

}
