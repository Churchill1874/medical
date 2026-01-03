package com.medical.pojo.req.prescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PrescriptionAdd implements Serializable {
    private static final long serialVersionUID = -2488231087915418640L;

    @ApiModelProperty("用户填写药品名称")
    private String medicalName;
    @ApiModelProperty("用户填写描述")
    private String description;
    @ApiModelProperty("图片 多个图片用,逗号隔开")
    private String image;
    @ApiModelProperty("收件人名称")
    private String username;
    @ApiModelProperty("收件人地址")
    private String address;
    @ApiModelProperty("收件人手机号")
    private String mobile;
    @ApiModelProperty("收件地址邮编")
    private String postCode;

}
