package com.medical.pojo.req.offlinetranslation;

import com.medical.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class OfflineTranslationUpdateStatus extends IdBase implements Serializable {
    private static final long serialVersionUID = 8295055654494218943L;

    @NotBlank(message = "请输入状态")
    @ApiModelProperty("0待受理 1审核中 2待付款 3预约成功 4预约取消")
    private String status;

}
