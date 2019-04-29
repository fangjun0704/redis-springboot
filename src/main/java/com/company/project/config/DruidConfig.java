package com.company.project.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfig {

  /**
   * 自己配置一个 DataSource 来覆盖默认的属性
   */
  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  public DataSource druid() {
    return new DruidDataSource();
  }

  /**
   * 配置一个管理后台的的 servlet
   */
  @Bean
  public ServletRegistrationBean statViewFilter() {
    ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    Map<String, String> params = new HashMap<>();
    params.put("loginUsername", "root");
    params.put("loginPassword", "root");
    params.put("allow", "");
    bean.setInitParameters(params);
    return bean;
  }

  /**
   * 配置一个web监控的的filter
   */
  @Bean
  public FilterRegistrationBean webStatFilter() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    bean.setFilter(new WebStatFilter());

    Map<String, String> params = new HashMap<>();
    params.put("exclusions", "*.js,*.css,/druid/*");

    bean.setInitParameters(params);
    bean.setUrlPatterns(Arrays.asList("/*"));
    return bean;

  }

}
