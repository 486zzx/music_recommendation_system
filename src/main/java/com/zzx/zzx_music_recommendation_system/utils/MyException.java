package com.zzx.zzx_music_recommendation_system.utils;

import com.zzx.zzx_music_recommendation_system.enums.ResCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 18:23
 */
@Data
@ApiModel(value = "自定义全局异常类")
public class MyException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    private String str;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public MyException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public MyException(String message) {
        super(message);
        this.code = ResCodeEnum.FAIL.getCode();
    }

    public MyException(String message, Exception e) {
        super(message);
        str = e.getMessage();
        this.code = ResCodeEnum.FAIL.getCode();
    }

    public MyException(ResCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        if (StringUtils.hasText(str)) {
            return "MyException{" +
                    "code=" + code +
                    ", message=" + this.getMessage() +
                    '}' + ':' + '\n' + str;
        }
        return "MyException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
