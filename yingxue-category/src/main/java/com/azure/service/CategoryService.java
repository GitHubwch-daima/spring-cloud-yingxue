package com.azure.service;

import com.azure.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wangch
 * @description 针对表【category(分类)】的数据库操作Service
 * @createDate 2022-03-25 20:46:49
 */
public interface CategoryService extends IService<Category> {

    List<Category> getCategories();

    Category saveCategory(Category category);
}
