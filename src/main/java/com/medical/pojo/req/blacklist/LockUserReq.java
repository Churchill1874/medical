package com.medical.pojo.req.blacklist;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LockUserReq implements Serializable {
    private static final long serialVersionUID = -4313522821084909773L;

    @NotNull(message = "未找到用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    @NotNull(message = "请指定封禁天数")
    @ApiModelProperty("封禁多少天 这个用户的ip的意思")
    private Integer days;


}
