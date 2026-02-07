package com.medical.pojo.req.onlineprescription;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlinePrescriptionPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 8215507479071000096L;

    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Integer status;

    @ApiModelProperty("真实姓名")
    private String realName;

}
