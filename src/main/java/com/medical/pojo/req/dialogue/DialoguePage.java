package com.medical.pojo.req.dialogue;

import com.medical.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DialoguePage extends PageBase implements Serializable {
    private static final long serialVersionUID = -4580760195609279844L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

    @ApiModelProperty("在线问诊表单id 含义:对话属于某个在线问诊提交的病情单子,从问诊单子入口进来看相关聊天")
    private Long onlineConsultationId;

    @ApiModelProperty("在线咨询处方药")
    private Long onlinePrescriptionId;

    @ApiModelProperty("1开处方药 2专家在线问诊")
    private Integer business;
}
