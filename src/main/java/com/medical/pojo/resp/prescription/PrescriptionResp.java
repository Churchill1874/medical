package com.medical.pojo.resp.prescription;

import com.medical.entity.Prescription;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PrescriptionResp extends Prescription implements Serializable {
    private static final long serialVersionUID = 3294074665249213010L;

    @ApiModelProperty("是否有未读新消息")
    private Boolean newMessage=false;

}
