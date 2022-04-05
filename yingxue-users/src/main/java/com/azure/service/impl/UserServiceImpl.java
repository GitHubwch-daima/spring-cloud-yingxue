package com.azure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.azure.entity.User;
import com.azure.service.UserService;
import com.azure.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author wangch
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-03-26 10:24:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




