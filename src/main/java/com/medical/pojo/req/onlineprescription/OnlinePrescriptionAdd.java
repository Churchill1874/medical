package com.medical.pojo.req.onlineprescription;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OnlinePrescriptionAdd extends PageBase implements Serializable {
    private static final long serialVersionUID = 6795965318331716685L;

    @ApiModelProperty("用户填写药品名称")
    private String medicalName;

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
    @ApiModelProperty("快递订单号")
    private String expressDeliveryNo;

    @NotBlank(message = "")
    @ApiModelProperty("当前症状描述")
    private String description;
    @NotBlank(message = "")
    @ApiModelProperty("症状持续时间")
    private String duration;
    @NotBlank(message = "")
    @ApiModelProperty("真实姓名")
    private String realName;
    @NotNull(message = "")
    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;
    @NotNull(message = "")
    @ApiModelProperty("性别")
    private Integer gender;
    @NotBlank(message = "")
    @ApiModelProperty("药物过敏史")
    private String allergyHistory;
    @NotBlank(message = "")
    @ApiModelProperty("既往病史")
    private String underlyingDiseases;
    @ApiModelProperty("当前正在使用的药品 备注:如果没有请写无,避免重复用药")
    private String currentMedical="无";
    @NotBlank(message = "")
    @ApiModelProperty("指定取药局(谷歌定位图片)")
    private String googleImage;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("其他补充信息")
    private String remark;

}
