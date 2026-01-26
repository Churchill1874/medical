package com.medical.pojo.req.onlineprescription;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OnlinePrescriptionUpdateStatus implements Serializable {
    private static final long serialVersionUID = 7315576712880311861L;

    @NotNull(message = "id不能为空")
    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Long id;

    @NotNull(message = "修改状态不能为空")
    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Integer status;

    @ApiModelProperty("备注修改原因")
    private String reason;

}
