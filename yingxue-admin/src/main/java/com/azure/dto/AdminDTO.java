package com.azure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 已登录的用户返回信息
 */
@Data
public class AdminDTO {
    //前端展示的是name，不会展示username
    @JsonProperty("name")
    private String username;
    private String avatar;
}
