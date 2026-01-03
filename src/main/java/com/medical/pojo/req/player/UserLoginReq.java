package com.medical.pojo.req.player;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserLoginReq implements Serializable {
    private static final long serialVersionUID = -713458171533157118L;

    @NotBlank(message = "账号不能为空")
    @Length(min = 8, max = 20, message = "账号长度8-20位之间")
    @ApiModelProperty("账号")
    private String username;

    @NotBlank(message = "请输入密码")
    @Length(min = 8, max = 20, message = "请输入20位以内的密码")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String verificationCode;

}
