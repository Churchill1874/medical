package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.medical.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 对话
 */
@Data
@TableName("dialogue")
public class Dialogue extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 6367585070354843120L;

    @ApiModelProperty("在线问诊表单id 含义:对话属于某个在线问诊提交的病情单子,从问诊单子入口进来看相关聊天")
    private Long onlineConsultationId;

    @ApiModelProperty("在线咨询处方药")
    private Long onlinePrescriptionId;

    @ApiModelProperty("是否是管理员发送的")
    private Boolean isAdmin;

    @ApiModelProperty("接受消息用户id")
    private Long receiveId;

    @ApiModelProperty("接受消息用户名称")
    private String receiveName;

    @ApiModelProperty("发送用户id")
    private Long sendId;

    @ApiModelProperty("用户名")
    private String sendName;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("1文字 2图片")
    private Integer type;

    @ApiModelProperty("是否已读")
    private Boolean isRead;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("读取时间")
    private LocalDateTime readTime;

    @ApiModelProperty("1处方药咨询 2重大疾病问诊")
    private Integer business;

}
