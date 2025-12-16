package com.medical.pojo.req.admin;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminPage extends PageBase {
    private static final long serialVersionUID = 8444530841212717465L;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("登录ip")
    private String loginIp;

}
