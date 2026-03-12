package com.medical.pojo.req.onlineconsultation;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlineConsultationPage extends PageBase implements Serializable {
    private static final long serialVersionUID = -5192338936074857008L;

    @ApiModelProperty("0待处理 1处理中 2取消 3完成")
    private Integer status;

    @ApiModelProperty("真实姓名")
    private String realName;

}
