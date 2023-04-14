package com.zzx.zzx_music_recommendation_system;

import lombok.Getter;

@Getter
public enum ResCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(500, "失败"),
    SERVICE_ERROR(202, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    SIGN_ERROR(300, "签名错误"),
    REPEAT_ERROR(402, "重复提交"),

    ;

    private Integer code;

    private String message;

    ResCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
