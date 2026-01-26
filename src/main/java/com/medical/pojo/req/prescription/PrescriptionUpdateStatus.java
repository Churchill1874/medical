package com.medical.pojo.req.prescription;

import com.medical.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PrescriptionUpdateStatus extends IdBase implements Serializable {
    private static final long serialVersionUID = 8244969896720669924L;

    @NotNull(message = "处理状态不能为空")
    @ApiModelProperty("0待处理 1处理中 2已完成")
    private Integer status;

    @ApiModelProperty("管理员的备注")
    private String reason;

}
