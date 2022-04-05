package com.azure.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.azure.entity.Admin;
import com.azure.service.AdminService;
import com.azure.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author wangch
 * @description 针对表【admin】的数据库操作Service实现
 * @createDate 2022-03-25 17:01:24
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    private AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @Override
    public Admin login(Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Admin::getUsername, admin.getUsername());
        Admin selectOne = adminMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(selectOne)) {
            throw new RuntimeException("用户名错误！");
        }
        String password = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!StringUtils.equals(password, selectOne.getPassword())) {
            throw new RuntimeException("密码输入错误！");
        }
        System.out.println("test");
        return selectOne;
    }
}




