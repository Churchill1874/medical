package com.medical.pojo.resp.offlinetranslation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class OfflineTranslationReport implements Serializable {
    private static final long serialVersionUID = 7971974112957147676L;

    @ApiModelProperty("当月")
    private Integer thisMonth = 0;
    @ApiModelProperty("上个月")
    private Integer lastMonth = 0;
    @ApiModelProperty("上上个月")
    private Integer beforeLastMonth = 0;
    @ApiModelProperty("当年")
    private Integer thisYear = 0;
    @ApiModelProperty("去年")
    private Integer lastYear = 0;

}
