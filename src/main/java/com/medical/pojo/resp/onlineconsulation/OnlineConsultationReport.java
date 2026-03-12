package com.medical.pojo.resp.onlineconsulation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OnlineConsultationReport implements Serializable {
    private static final long serialVersionUID = -8230990553455613788L;

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
