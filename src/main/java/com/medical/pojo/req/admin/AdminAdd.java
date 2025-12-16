package com.medical.pojo.req.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AdminAdd implements Serializable {
    private static final long serialVersionUID = -8328317163525683573L;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "名字不能为空")
    @ApiModelProperty(value = "名字", required = true)
    private String name;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", required = true)
    private String account;

}
