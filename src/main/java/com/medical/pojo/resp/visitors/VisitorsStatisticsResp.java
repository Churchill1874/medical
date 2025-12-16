package com.medical.pojo.resp.visitors;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class VisitorsStatisticsResp implements Serializable {
    private static final long serialVersionUID = -5695713899725358111L;

    @ApiModelProperty("今日ip数量")
    private long today = 0;
    @ApiModelProperty("今日ip地址来源地址")
    private Set<String> todayAddress;

    @ApiModelProperty("昨天ip数量")
    private long yesterday = 0;
    @ApiModelProperty("昨日ip地址来源地址")
    private Set<String> yesterdayAddress;

    @ApiModelProperty("前天ip数量")
    private long beforeYesterday = 0;
    @ApiModelProperty("前天ip地址来源地址")
    private Set<String> beforeYesterdayAddress;

    @ApiModelProperty("本周ip数量")
    private long thisWeek = 0;
    @ApiModelProperty("上一周ip数量")
    private long lastWeek = 0;

    @ApiModelProperty("本月ip数量")
    private long thisMonth = 0;

    @ApiModelProperty("上一个月ip数量")
    private long lastMonth = 0;
    @ApiModelProperty("上上个月ip数量")
    private long beforeMonth = 0;




}
