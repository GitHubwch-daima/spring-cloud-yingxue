package com.azure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.azure.entity.Category;
import com.azure.service.CategoryService;
import com.azure.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * @author wangch
 * @description 针对表【category(分类)】的数据库操作Service实现
 * @createDate 2022-03-25 20:46:49
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    public List<Category> getCategories() {
        return categoryMapper.getCategoriesDao();
    }

    @Override
    public Category saveCategory(Category category) {
        Date date = new Date();
        category.setCreatedAt(date);
        category.setUpdatedAt(date);
        this.categoryMapper.saveCategoryDao(category);
        return category;
    }
}




