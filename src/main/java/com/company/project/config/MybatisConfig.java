package com.company.project.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

  /**
   * 开启驼峰规则（mybatis默认不开启） 将实体类中的 testField 对应数据库表中的 test_field
   *
   * 也可以在application.properties配置 mybatis.configuration.map-underscore-to-camel-case=true
   */
  @Bean
  public ConfigurationCustomizer configurationCustomizer() {
    return configuration -> {
      configuration.setMapUnderscoreToCamelCase(true);
    };
  }

}
