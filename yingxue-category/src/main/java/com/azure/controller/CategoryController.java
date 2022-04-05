package com.azure.controller;


import com.azure.utils.StringUtils;
import com.azure.entity.Category;
import com.azure.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 删除类别
     *
     * @param id 类别id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        categoryService.removeById(id);
    }


    /**
     * 保存类别信息
     *
     * @param category 类别
     * @return 类别（更新之后的类别包括id）
     */
    @PostMapping("save")
    public Category save(@RequestBody Category category) {
        log.info("保存类别信息：{}", StringUtils.writeJson(category));
        Category saveCategory = categoryService.saveCategory(category);
        log.info("保存之后的类别信息：{}", StringUtils.writeJson(category));
        return saveCategory;
    }


    /**
     * 更新类别
     *
     * @param id       主键
     * @param category 更新的信息
     * @return 类别
     */
    @PatchMapping("/{id}")
    public Category update(@PathVariable("id") Integer id, @RequestBody Category category) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<>();
        category.setId(id);
        updateWrapper.lambda().eq(Category::getId, id);
        boolean update = categoryService.update(category, updateWrapper);
        if (update) {
            return categoryService.getById(id);
        }
        throw new RuntimeException("更新失败！！！");
    }


    /**
     * 类别列表
     *
     * @return 全部的信息
     */
    @GetMapping("categories")
    public List<Category> categories() {
        return categoryService.getCategories();
    }


}
