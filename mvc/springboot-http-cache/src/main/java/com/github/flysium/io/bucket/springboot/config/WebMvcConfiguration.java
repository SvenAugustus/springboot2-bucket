package com.github.flysium.io.bucket.springboot.config;

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
