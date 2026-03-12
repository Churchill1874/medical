package com.medical.pojo.req.prescription;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PrescriptionPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -660015091564629637L;

    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Integer status;
    @ApiModelProperty("后台管理是否已读")
    private Boolean readStatus;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("用户登录账号")
    private String account;
    @ApiModelProperty("用户药品名称")
    private String medicalName;
    @ApiModelProperty("收件人名称")
    private String username;
    @ApiModelProperty("收件人手机号")
    private String mobile;
    @ApiModelProperty("微信号")
    private String wechat;

}
