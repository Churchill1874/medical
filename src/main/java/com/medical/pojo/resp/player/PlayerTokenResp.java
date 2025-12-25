package com.medical.pojo.resp.player;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("用户token")
public class PlayerTokenResp implements Serializable {
    private static final long serialVersionUID = -773689514264864093L;

    @ApiModelProperty("玩家id")
    private Long id;
    @ApiModelProperty("令牌")
    private String tokenId;
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("真实名称")
    private String realName;
    @ApiModelProperty("状态 1正常 0禁用")
    private Integer status;
    @ApiModelProperty("登陆时间")
    private LocalDateTime loginTime;

}
