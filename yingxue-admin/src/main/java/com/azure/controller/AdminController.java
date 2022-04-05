package com.azure.controller;

import com.azure.constants.RedisPrefix;
import com.azure.utils.StringUtils;
import com.azure.dto.AdminDTO;
import com.azure.entity.Admin;
import com.azure.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    private final RedisTemplate redisTemplate;

    @Autowired
    private AdminController(AdminService adminService, RedisTemplate redisTemplate) {
        this.adminService = adminService;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 用户登出接口
     *
     * @param token
     */
    @DeleteMapping("/tokens/{token}")
    public void logout(@PathVariable("token") String token) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(RedisPrefix.TOKEN_KEY +token);
    }


    /**
     * 获取用户登录信息的接口
     *
     * @param token
     * @return
     */
    @GetMapping("/admin-user")
    public AdminDTO admin(String token) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Admin admin = (Admin) redisTemplate.opsForValue().get(token);
        AdminDTO adminDTO = new AdminDTO();
        //将admin的属性复制给adminDTO
        assert admin != null;
        BeanUtils.copyProperties(admin, adminDTO);
        return adminDTO;
    }


    /**
     * 用户登录接口
     *
     * @param admin
     * @param session
     * @return
     */
    @PostMapping("/tokens")
    public Map<String, String> login(@RequestBody Admin admin, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        //利用jackson将对象转为json
        log.error("登录接口，接受到的admin对象为，{}", StringUtils.writeJson(admin));
        Admin adminDB = adminService.login(admin);
        String token = session.getId();
        //序列化redis的key为String
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置超时时间为半个小时
        redisTemplate.opsForValue().set(RedisPrefix.TOKEN_KEY + token, adminDB, 30, TimeUnit.MINUTES);
        result.put("token", token);
        return result;
    }
}
