package com.azure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient//开启nacos
@MapperScan("com.azure.mapper")
public class VideosApplication {
    public static void main(String[] args) {
        SpringApplication.run(VideosApplication.class, args);
    }
}
