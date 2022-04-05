package com.azure.mapper;

import com.azure.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangch
 * @description 针对表【category(分类)】的数据库操作Mapper
 * @createDate 2022-03-25 20:46:49
 * @Entity com.azure.entity.Category
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    void saveCategoryDao(Category category);

    List<Category> getCategoriesDao();
}




