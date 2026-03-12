package com.medical.pojo.req.dialogue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OnlinePrescriptionDialogueSend implements Serializable {
    private static final long serialVersionUID = -540711963088070983L;

    @ApiModelProperty("在线咨询药品问诊表单id 含义:对话属于某个在线问诊提交的病情单子,从问诊单子入口进来看相关聊天")
    private Long onlinePrescriptionId;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("内容")
    private String content;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("1文字 2图片")
    private Integer type;

}
