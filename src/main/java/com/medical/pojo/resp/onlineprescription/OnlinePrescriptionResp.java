package com.medical.pojo.resp.onlineprescription;

import com.medical.entity.OnlinePrescription;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class OnlinePrescriptionResp extends OnlinePrescription implements Serializable {
    private static final long serialVersionUID = -5872182618039245169L;

    @ApiModelProperty("是否有未读新消息")
    private Boolean newMessage=false;
}
