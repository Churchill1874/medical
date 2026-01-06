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
    @ApiModelProperty("直接输入中文 : 1.预防医疗 2.再生医疗 3.医疗美容 4.普通医疗")
    private String medicalType;

    @NotBlank(message = "请选择项目类型")
    @ApiModelProperty("直接输入中文 项目类型  1.预防医疗(1.精密体检 2.血液净化) 2.再生医疗(1.自体脂肪干细胞 2.nk细胞 3.纤维芽细胞) 3.医疗美容(1.轻医美 2.外科手术 3.修复手术) 4.普通医疗(1.专科诊所 2.综合医院 3.介绍信)")
    private String projectType;

    @NotNull(message = "请输入性别")
    @ApiModelProperty("性别 1男 0女")
    private Integer gender;

    @NotNull(message = "请输入年龄")
    @ApiModelProperty("年龄")
    private Integer age;

    @NotBlank(message = "请输入真实姓名")
    @ApiModelProperty("真实名称")
    private String realName;

    @NotBlank(message = "请输入邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "请输入手机号")
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull(message = "请输入生日")
    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @NotBlank(message = "请输入过敏史")
    @ApiModelProperty("过敏史")
    private String allergyHistory;

    @NotBlank(message = "请输入病情描述")
    @ApiModelProperty("描述")
    private String description;

    @NotBlank(message = "请输入预算")
    @ApiModelProperty("预算 5000人民币以下 5000-1万人民币 1万到3万人民币 3万以上")
    private String budget;

    @NotBlank(message = "请输入微信号")
    @ApiModelProperty("微信号")
    private String wechat;



}
