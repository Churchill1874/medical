package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 处方药
 */
@Data
@TableName("prescription")
public class Prescription extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 1819175993252694651L;

    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("登录账号")
    private String account;
    @ApiModelProperty("用户填写药品名称")
    private String medicalName;
    @ApiModelProperty("用户填写描述")
    private String description;
    @ApiModelProperty("图片 多个图片用,逗号隔开")
    private String image;
    @ApiModelProperty("0待处理 1已处理 2处理中")
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
    @ApiModelProperty("管理员的回复备注 比如大概多久到啊什么的,或者不能给开药之类的原因")
    private String remark;
    @ApiModelProperty("快递订单号")
    private String expressDeliveryNo;
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("修改人")
    private String updateName;


}
