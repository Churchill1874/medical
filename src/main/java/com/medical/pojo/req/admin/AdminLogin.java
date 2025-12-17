package com.medical.pojo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AdminLogin implements Serializable {
    private static final long serialVersionUID = -5142001742887442327L;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty("账号")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

    @NotBlank(message = "请输入验证码")
    @ApiModelProperty("验证码")
    private String verifyCode;

}
