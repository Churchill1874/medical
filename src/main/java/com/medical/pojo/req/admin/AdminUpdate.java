package com.medical.pojo.req.admin;

import com.medical.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AdminUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -7884649139919074869L;

    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
