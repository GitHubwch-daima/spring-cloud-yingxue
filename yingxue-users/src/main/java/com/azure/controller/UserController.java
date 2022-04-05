package com.azure.controller;

import com.azure.entity.User;
import com.azure.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * @param pageNow 当前页 必传的参数，不传默认是1
     * @param rows    每页显示的条数 必传的参数，不传默认是5
     * @param id      搜索的值 required = false 非必须传的参数
     * @param name    name
     * @param phone   phone
     * @return result
     */
    @GetMapping
    public Map<String, Object> users(@RequestParam(value = "page", defaultValue = "1") Integer pageNow,
                                     @RequestParam(value = "per_page", defaultValue = "5") Integer rows,
                                     @RequestParam(required = false) String id, String name, String phone) {
        Map<String, Object> result = new HashMap<>();
        log.info("分页信息 当前页：{}，每页显示的条数：{}", pageNow, rows);
        log.info("搜索的值：id{}，name：{}，phone：{}", id, name, phone);
        Page<User> page = new Page<>(pageNow, rows);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(id), User::getFollowersCount, id)
                .like(StringUtils.isNotBlank(name), User::getName, name)
                .eq(StringUtils.isNotBlank(phone), User::getPhone, phone);
        Page<User> userPage = userService.page(page, queryWrapper);
        log.info("当前页：{}", page.getCurrent());
        log.info("当前list中的总数：{}", userPage.getSize());
        log.info("当前符合条件的总数：{}", page.getTotal());
        result.put("total_count", page.getTotal());
        result.put("items", userPage);
        return result;
    }

}
