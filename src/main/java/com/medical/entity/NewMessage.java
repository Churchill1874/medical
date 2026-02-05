package com.medical.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.medical.entity.base.BaseInfo;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("new_message")
public class NewMessage extends BaseInfo implements Serializable {
    private static final long serialVersionUID = 5348124006676032178L;

    //1未读对话信息 2在线开处方药状态变化 3在线处方药咨询 4线下陪同状态变化 5重大疾病在线咨询问诊
    private Integer type;

    private String medicalType;

    //内容
    private String content;

    //业务id
    private Long orderId;

    private Long userId;


}
