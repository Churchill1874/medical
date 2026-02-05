package com.medical.pojo.resp.onlineconsulation;

import com.medical.entity.OnlineConsultation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlineConsultationResp extends OnlineConsultation implements Serializable {
    private static final long serialVersionUID = 4952160744703862179L;

    @ApiModelProperty("是否有未读新消息")
    private Boolean newMessage = false;
}
