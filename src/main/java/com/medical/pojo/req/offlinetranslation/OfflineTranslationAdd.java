package com.medical.pojo.req.offlinetranslation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OfflineTranslationAdd implements Serializable {
    private static final long serialVersionUID = 5826350233249171110L;

    @NotBlank(message = "请选择医疗类型")
    @ApiModelProperty(value = "直接输入中文 : 1.预防医疗 2.再生医疗 3.医疗美容 4.普通医疗",required = true)
    private String medicalType;

    @ApiModelProperty(value = "直接输入中文 项目类型  " + "如果是" +
            "预防医疗(1.精密体检 2.癌症筛查 3.血液净化 4.其他)" +
            "再生医疗(1.自体脂肪干细胞 2.异体干细胞上清液 3.nk细胞 4.纤维芽细胞 5.prp 6.其他) " +
            "医疗美容(1.轻医美 2.外科手术 3.修复手术) " +
            "普通医疗 暂时普通医疗没有项目选择")
    private String projectType;

    @NotBlank(message = "请输入真实姓名")
    @ApiModelProperty(value = "真实名称",required = true)
    private String realName;

    @NotNull(message = "请输入生日")
    @ApiModelProperty(value = "出生年月日 如:2000-10-01",required = true)
    private LocalDate birthday;

    @NotNull(message = "请输入性别")
    @ApiModelProperty(value = "性别 1男 0女",required = true)
    private Integer gender;

    @NotBlank(message = "请输入病情描述")
    @ApiModelProperty(value = "描述",required = true)
    private String description;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank(message = "请输入微信号")
    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "过敏史")
    private String allergyHistory;

    @ApiModelProperty(value = "预算 5000人民币以下 5000-1万人民币 1万到3万人民币 3万以上")
    private String budget;

    @ApiModelProperty("预约时间")
    private String appointmentTime;

    @ApiModelProperty("是否已经有意向医院(有 / 无)")
    private String targetHospital;

    @ApiModelProperty("补充信息")
    private String remark;


}
