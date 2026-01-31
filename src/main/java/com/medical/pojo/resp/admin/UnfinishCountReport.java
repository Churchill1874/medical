package com.medical.pojo.resp.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UnfinishCountReport implements Serializable {
    private static final long serialVersionUID = -6963839217901079569L;

    @ApiModelProperty("待处理订单 线下陪同翻译订单数量")
    private int offTranslationCount = 0;

    @ApiModelProperty("待处理订单 重大疾病在线问诊订单数量")
    private int onlineConsultationCount = 0;

    @ApiModelProperty("待处理订单 在线咨询处方药订单数量")
    private int onlinePrescriptionCount = 0;

    @ApiModelProperty("待处理订单 开药订单数量")
    private int prescriptionCount = 0;

}
