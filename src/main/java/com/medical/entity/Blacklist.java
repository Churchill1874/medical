package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("blacklist")
public class Blacklist extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -1202157531975938418L;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("解封时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime unlockingTime;

}
