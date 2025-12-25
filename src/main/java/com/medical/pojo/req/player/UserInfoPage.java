package com.medical.pojo.req.player;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserInfoPage extends PageBase {
    private static final long serialVersionUID = 4153923915533702051L;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;


}
