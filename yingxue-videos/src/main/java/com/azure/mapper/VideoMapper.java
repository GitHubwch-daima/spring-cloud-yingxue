package com.azure.mapper;

import com.azure.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangch
 * @description 针对表【video(视频)】的数据库操作Mapper
 * @createDate 2022-03-26 12:23:11
 * @Entity com.azure.entity.Video
 */
@Repository
public interface VideoMapper extends BaseMapper<Video> {


    /**
     * @param id         视频id
     * @param name       视频名称
     * @param categoryId 类别id
     * @param username   用户名
     * @return 条件符合条数
     */
    Long findTotalCountsByKeywords(@Param("id") String id,
                                   @Param("title") String name,
                                   @Param("categoryId") String categoryId,
                                   @Param("username") String username);


    /**
     * 条件分页查询
     *
     * @param start      起始位置
     * @param rows       每页显示记录数
     * @param id         视频id
     * @param name       视频名称
     * @param categoryId 类别id
     * @param username   用户名
     */
    List<Video> findAllByKeywords(@Param("start") int start,
                                  @Param("rows") int rows,
                                  @Param("id") String id,
                                  @Param("title") String name,
                                  @Param("categoryId") String categoryId,
                                  @Param("username") String username);

}




