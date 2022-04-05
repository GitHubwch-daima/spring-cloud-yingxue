package com.azure.gateway.filter.factory;

import com.azure.constants.RedisPrefix;
import com.azure.exception.IllegalTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class TokenGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(TokenGatewayFilterFactory.class);

    private RedisTemplate redisTemplate;

    @Autowired
    public TokenGatewayFilterFactory(RedisTemplate redisTemplate) {
        //这里要和redis一起构造
        super(TokenGatewayFilterFactory.Config.class);
        this.redisTemplate = redisTemplate;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                log.info("config required token:{}", config.requireToken);
                if (config.requireToken) {
                    //判断是否有token，之后再进行获取，不然会空指针异常
                    if (exchange.getRequest().getQueryParams().get("token") == null) {
                        throw new IllegalTokenException("非法令牌！！！");
                    }
                    //获取交换机里的token，这里的token是list集合，这里只获取第一个
                    String token = exchange.getRequest().getQueryParams().get("token").get(0);
                    log.info("token:{}", token);
                    // 判断redis里是否有这个key
                    // (!redisTemplate.hasKey(RedisPrefix.TOKEN_KEY + token)
                    if (Boolean.FALSE.equals(redisTemplate.hasKey(RedisPrefix.TOKEN_KEY + token))) {
                        throw new IllegalTokenException("令牌redis里没有！！！");
                    }
                }
                //放行
                return chain.filter(exchange);
            }
        };
    }

    /**
     * 用来配置将使用filter时，将指定赋值非config中的那个属性
     *
     * @return Arrays.asList(token);（将token转为list集合）
     */
    @Override
    public List<String> shortcutFieldOrder() {
        //Arrays.asList("token");
        return Collections.singletonList("requireToken");
    }


    //自定义配置类(如果Token后边是true，就要校验是否有缓存)
    public static class Config {

        //默认是false（这个属性可以写多个，之后用Arrays.asList("token");获取就好了）
        private boolean requireToken;

        //配置文件中可以这么写Token=true,xxx,xxx
        //private String name;

        public boolean isRequireToken() {
            return requireToken;
        }

        public void setRequireToken(boolean requireToken) {
            this.requireToken = requireToken;
        }
    }
}
