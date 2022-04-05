package com.azure.config;

import com.azure.exception.IllegalTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GlobalExceptionConfiguration implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionConfiguration.class);

    /**
     * @param exchange  response
     * @param exception exception
     * @return Mono
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable exception) {
        //1、获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //2、判断是否结束
        if (response.isCommitted()) {
            return Mono.error(exception);
        }
        //3、设置响应头类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        //4、设置响应状态码，判断是不是自定义的异常
        // (左边是对象，右边是类；当对象是右边类或子类所创建对象时，返回true；否则，返回false)
        if (exception instanceof IllegalTokenException) {
            //状态码：403没有权限访问
            response.setStatusCode(HttpStatus.FORBIDDEN);
        } else {
            //状态码：500服务器内部错误
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response.writeWith(Mono.fromSupplier(() -> {
            Map<String, Object> result = new HashMap<>();
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            result.put("msg", exception.getMessage());
            log.info("exception:{}", exception.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                //将结果转成byte，这样返回的就是json格式
                return dataBufferFactory.wrap(objectMapper.writeValueAsBytes(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }));
    }
}
