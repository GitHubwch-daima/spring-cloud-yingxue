package com.azure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 视频
 *
 * @TableName video
 */
@TableName(value = "video")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video implements Serializable {
    /**
     * 主键id（视频表）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String intro;

    /**
     * up主id
     */
    private Integer uid;

    /**
     * 视频封面链接
     */
    private String cover;

    /**
     * 视频播放链接
     */
    private String link;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;


    /**
     * 删除时间
     */
    private Date deletedAt;

    /**
     * 类别信息
     */
    private String category;

    /**
     * 用户信息
     */
    private User uploader;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}