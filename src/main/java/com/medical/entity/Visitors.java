package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 访问记录
 */
@Data
@TableName("visitors")
public class Visitors extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -8613256443103577781L;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("浏览器指纹")
    private String userAgent;

}
