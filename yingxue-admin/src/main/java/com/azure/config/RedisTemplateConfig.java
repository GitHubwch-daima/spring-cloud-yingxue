package com.azure.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {

    /**
     * 设置redis对象的序列化方式
     *
     * @param redisTemplate redisTemplate
     */
    @Autowired
    public RedisTemplateConfig(RedisTemplate redisTemplate) {

        //1、创建jackson序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //2、拆改那就object mapper
        ObjectMapper objectMapper = new ObjectMapper();
        //3、允许访问对象个所有属性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //4、转化json过程中保存类的信息（例：username:test······）
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //5、设置序列化的方式key为string，value为json
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //6、jackson2JsonRedisSerializer序列化方式就是json的序列号规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //6.5、设置hash类型的序列化方式（hash类型有两个key一个value）
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //7、工厂创建redisTemplate对象之后再进行配置
        redisTemplate.afterPropertiesSet();
    }
}
