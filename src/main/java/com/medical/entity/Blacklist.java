package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("black_list")
public class Blacklist extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -1202157531975938418L;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("备注")
    private String remark;

}
