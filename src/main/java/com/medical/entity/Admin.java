package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("admin")
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 8633321552782077838L;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("登录ip")
    private String loginIp;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("登录地址")
    private String address;

    @TableField(exist = false)
    @ApiModelProperty("令牌")
    private String tokenId;

}
