package com.medical.pojo.req.player;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserInfoAdd implements Serializable {
    private static final long serialVersionUID = 2963284788987492939L;

    @Length(min = 4, max = 20,message = "账号长度8-20位之间")
    @NotBlank(message = "请输入账号")
    @ApiModelProperty("账号")
    private String username;

    @Length(min = 8, max = 20,message = "请输入8-20位长的密码")
    @NotBlank(message = "请输入密码")
    @ApiModelProperty("密码")
    private String password;

    @NotNull(message = "请填写性别")
    @ApiModelProperty("性别: 1男 0女")
    private Integer gender;

    @Length(max = 100,message = "请输入100位长度以内的邮箱")
    @NotBlank(message = "请填写邮箱,方便密码找回")
    @ApiModelProperty("邮箱")
    private String email;

    @Length(max = 30,message = "请先输入手机号")
    @NotBlank(message = "请输入手机号,方便密码找回")
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull(message = "请填写出生年月")
    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @Length(max = 10,message = "请先输入验证码")
    @NotBlank(message = "请输入验证码")
    @ApiModelProperty("验证码")
    private String verifyCode;

    @NotBlank(message = "请填写如何知道的该网站")
    @ApiModelProperty("如何知道的该网站,直接传入中文 1.朋友推荐 2.网站网页看到的 3.社交软件看到的 4.其他")
    private String source;


}
