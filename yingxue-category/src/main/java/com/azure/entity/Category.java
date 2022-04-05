package com.azure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 分类
 *
 * @TableName category
 */
@TableName(value = "category")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//用在类上，返回属性不为空的
public class Category implements Serializable {
    /**
     * 主键id（类别表）
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级分类id
     */
    //不仅用于序列化也适用于反序列化
    @JsonProperty("parent_id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 删除时间
     */
    private Date deletedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 孩子属性,有多个孩子
     */
    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的。
    private List<Category> children;

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

}