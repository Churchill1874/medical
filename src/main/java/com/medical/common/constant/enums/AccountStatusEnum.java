package com.medical.common.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 用户状态枚举
 */
public enum AccountStatusEnum {

    DISABLE("禁用",0),
    NORMAL("正常",1);

    @Getter
    @EnumValue
    @JsonValue
    private Integer code;

    @Getter
    private String name;

    AccountStatusEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.code;
    }

}
