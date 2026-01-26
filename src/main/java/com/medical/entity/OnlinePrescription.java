package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 在线咨询处方药
 */
@Data
@TableName("online_prescription")
public class OnlinePrescription extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 6277334428325580844L;

    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("登录账号")
    private String account;
    @ApiModelProperty("用户填写药品名称")
    private String medicalName;

    @ApiModelProperty("药品图片 多个图片用,逗号隔开")
    private String image;
    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Integer status;
    @ApiModelProperty("后台管理是否已读")
    private Boolean readStatus;
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
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("修改人")
    private String updateName;

    @ApiModelProperty("当前症状描述")
    private String description;
    @ApiModelProperty("症状持续时间")
    private String duration;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("药物过敏史")
    private String allergyHistory;
    @ApiModelProperty("既往病史")
    private String underlyingDiseases;
    @ApiModelProperty("当前正在使用的药品 备注:如果没有请写无,避免重复用药")
    private String currentMedical;
    @ApiModelProperty("指定取药局(谷歌定位图片)")
    private String googleImage;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("其他补充信息")
    private String remark;
    @ApiModelProperty("原因")
    private String reason;


}
