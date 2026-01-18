package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 在线问诊
 */
@Data
@TableName("online_consultation")
public class OnlineConsultation extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -3231101775054760835L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("症状描述")
    private String symptom;

    @ApiModelProperty("持续时间")
    private String duration;

    @ApiModelProperty("过敏史")
    private String allergyHistory;

    @ApiModelProperty("基础病描述")
    private String underlyingDiseases;

    @ApiModelProperty("0待处理 1处理中 2取消 3完成")
    private Integer status;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty("描述")
    private String description;

}
