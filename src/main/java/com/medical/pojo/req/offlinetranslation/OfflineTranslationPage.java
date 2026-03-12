package com.medical.pojo.req.offlinetranslation;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OfflineTranslationPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -3916317957972729174L;

    @ApiModelProperty("直接输入中文: 1.预防医疗 2.再生医疗 3.医疗美容 4.普通医疗")
    private String medicalType;

    @ApiModelProperty("直接输入中文: 项目类型  1.预防医疗(1.精密体检 2.血液净化) 2.再生医疗(1.自体脂肪干细胞 2.nk细胞 3.纤维芽细胞) 3.医疗美容(1.轻医美 2.外科手术 3.修复手术) 4.普通医疗(1.专科诊所 2.综合医院 3.介绍信)")
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

    @ApiModelProperty("直接输入中文: 0待受理 1审核中 2待付款 3预约成功 4预约取消")
    private String status;

    @ApiModelProperty("微信号")
    private String wechat;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户账号")
    private String username;


}
