package com.medical.pojo.req.prescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PrescriptionAdd implements Serializable {
    private static final long serialVersionUID = -2488231087915418640L;

    @NotBlank(message = "请输入姓名")
    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;
    @NotNull(message = "请输入出生年月")
    @ApiModelProperty(value = "出生年月日 如:2000-10-01", required = true)
    private LocalDate birthday;
    @NotNull(message = "请输入性别")
    @ApiModelProperty(value = "性别", required = true)
    private Integer gender;
    @NotBlank(message = "请输入过敏史")
    @ApiModelProperty(value = "过敏史", required = true)
    private String allergyHistory;
    @NotBlank(message = "请输入基础病")
    @ApiModelProperty(value = "基础病描述", required = true)
    private String underlyingDiseases;
    @NotBlank(message = "请输入指定取药局")
    @ApiModelProperty(value = "指定取药局(谷歌定位图片)", required = true)
    private String googleImage;
    @NotBlank(message = "请输入联系电话")
    @ApiModelProperty(value = "联系电话", required = true)
    private String phone;
    @NotBlank(message = "请输入邮箱")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @ApiModelProperty("药品名称")
    private String medicalName;
    @ApiModelProperty("药品描述")
    private String description;
    @ApiModelProperty("药品图片 多个图片用,逗号隔开")
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
