package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.UpdateBaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@TableName("offline_translation")
public class OfflineTranslation extends UpdateBaseInfo implements Serializable {
    private static final long serialVersionUID = 6686206665982570558L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户账号")
    private String username;

    @ApiModelProperty("1.预防医疗 2.再生医疗 3.医疗美容 4.普通医疗")
    private String medicalType;

    @ApiModelProperty("项目类型  1.预防医疗(1.精密体检 2.血液净化) 2.再生医疗(1.自体脂肪干细胞 2.nk细胞 3.纤维芽细胞) 3.医疗美容(1.轻医美 2.外科手术 3.修复手术) 4.普通医疗(1.专科诊所 2.综合医院 3.介绍信)")
    private String projectType;

    @ApiModelProperty("性别 1男 0女")
    private Integer gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("真实名称")
    private String realName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("出生年月日 如:2000-10-01")
    private LocalDate birthday;

    @ApiModelProperty("过敏史")
    private String allergyHistory;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("预算 5000人民币以下 5000-1万人民币 1万到3万人民币 3万以上")
    private String budget;

    @ApiModelProperty("0待受理 1审核中 2待付款 3预约成功 4预约取消")
    private String status;

    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty("预约时间")
    private String appointmentTime;


}
