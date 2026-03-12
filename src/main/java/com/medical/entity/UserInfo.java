package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
@TableName("user_info")
public class UserInfo extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 2034763065706783220L;

    @ApiModelProperty("真实名称")
    private String realName;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("状态 1正常 0禁用")
    private Integer status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("最后一次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("如何知道的该网站,直接传入中文 1.朋友推荐 2.网站网页看到的 3.社交软件看到的 4.其他")
    private String source;

}
