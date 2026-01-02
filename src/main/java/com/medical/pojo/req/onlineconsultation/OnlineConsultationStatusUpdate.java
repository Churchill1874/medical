package com.medical.pojo.req.onlineconsultation;

import com.medical.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlineConsultationStatusUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 5681403370962410395L;

    @ApiModelProperty("0待处理 1处理中 2取消 3完成")
    private Integer status;

}
