package com.azure.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.azure.entity.Video;
import com.azure.service.VideoService;
import com.azure.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangch
 * @description 针对表【video(视频)】的数据库操作Service实现
 * @createDate 2022-03-26 12:23:11
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
        implements VideoService {


    private final VideoMapper videoMapper;

    @Autowired
    public VideoServiceImpl(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Long findTotalCountsByKeywords(String id, String name, String
            categoryId, String username) {
        return videoMapper.findTotalCountsByKeywords(id, name, categoryId, username);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Video> findAllByKeywords(Integer page, Integer rows,
                                         String id, String name, String categoryId, String username) {
        int start = (page - 1) * rows;
        return videoMapper.findAllByKeywords(start, rows, id, name, categoryId, username);
    }

}




