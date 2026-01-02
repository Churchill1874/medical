package com.medical.pojo.req.dialogue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DialogueSend implements Serializable {
    private static final long serialVersionUID = 5006342787246074904L;

    @NotNull(message = "问诊id不能为空")
    @ApiModelProperty("在线问诊表单id 含义:对话属于某个在线问诊提交的病情单子,从问诊单子入口进来看相关聊天")
    private Long onlineConsultationId;

    @NotNull(message = "接受消息用户id不能为空")
    @ApiModelProperty("接受消息用户id")
    private Long receiveId;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("内容")
    private String content;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("1文字 2图片")
    private Integer type;

}

