package com.company.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyRedisConfig {

  @Bean
  public RedisTemplate redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    //修改redis默认的 序列化对象的规则(JdkSerializationRedisSerializer)
    Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
    // 设置value的序列化规则和 key的序列化规则
    redisTemplate.setValueSerializer(serializer);
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    ObjectMapper objectMapper = new ObjectMapper();
    //将时间格式化后 存入redis 默认是long的秒数
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    serializer.setObjectMapper(objectMapper);
    redisTemplate.setDefaultSerializer(serializer);
    return redisTemplate;
  }


}
