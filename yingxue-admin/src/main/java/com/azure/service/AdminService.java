package com.azure.service;

import com.azure.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author wangch
* @description 针对表【admin】的数据库操作Service
* @createDate 2022-03-25 17:01:24
*/
public interface AdminService extends IService<Admin> {

    Admin login(Admin admin);
}
