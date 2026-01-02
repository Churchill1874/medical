package com.medical.pojo.req.onlineconsultation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OnlineConsultationAdd implements Serializable {
    private static final long serialVersionUID = 8055180078714345929L;

    @NotBlank(message = "请输入真实真实")
    @ApiModelProperty("真实姓名")
    private String realName;

    @NotNull(message = "请输入出生年月日")
    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @NotNull(message = "请输入性别")
    @ApiModelProperty("性别")
    private Integer gender;

    @NotBlank(message = "请输入症状描述")
    @ApiModelProperty("症状描述")
    private String symptom;

    @NotBlank(message = "请输入持续时间")
    @ApiModelProperty("持续时间")
    private String duration;

    @NotBlank(message = "请输入过敏史")
    @ApiModelProperty("过敏史")
    private String allergyHistory;

    @NotBlank(message = "请输入急出病描述")
    @ApiModelProperty("基础病描述")
    private String underlyingDiseases;

}
