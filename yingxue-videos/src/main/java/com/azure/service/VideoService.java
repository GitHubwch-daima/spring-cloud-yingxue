package com.azure.service;

import com.azure.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wangch
 * @description 针对表【video(视频)】的数据库操作Service
 * @createDate 2022-03-26 12:23:11
 */
public interface VideoService extends IService<Video> {

    Long findTotalCountsByKeywords(String id, String name, String categoryId, String username);

    List<Video> findAllByKeywords(Integer page, Integer rows, String id, String name, String categoryId, String username);
}
