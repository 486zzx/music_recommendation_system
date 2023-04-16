package com.zzx.zzx_music_recommendation_system.vo;

import com.zzx.zzx_music_recommendation_system.enums.ResCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/5 20:29
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class ResVO<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public void Result(){}


    public static <T> ResVO<T> build(T data) {
        ResVO<T> result = new ResVO<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> ResVO<T> build(T body, ResCodeEnum resCodeEnum) {
        ResVO<T> result = build(body);
        result.setCode(resCodeEnum.getCode());
        result.setMessage(resCodeEnum.getMessage());
        return result;
    }

    public static<T> ResVO<T> ok(){
        return ResVO.ok((T) null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResVO<T> ok(T data){
        ResVO<T> result = build(data);
        return build(data, ResCodeEnum.SUCCESS);
    }

    public static<T> ResVO<T> ok(String message){
        ResVO<T> result = result(true ,message);
        return result;
    }

    public static<T> ResVO<T> fail(String message){
        ResVO<T> result = result(false ,message);
        return result;
    }

    public static<T> ResVO<T> fail(){
        return ResVO.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> ResVO<T> fail(T data){
        ResVO<T> result = build(data);
        return build(data, ResCodeEnum.FAIL);
    }

    public static<T> ResVO<T> fail(Integer code, String str){
        return fail(code, str, null);
    }

    public static<T> ResVO<T> fail(Integer code, String str, T data){
        ResVO<T> result = build(data);
        result.setCode(code);
        result.setMessage(str);
        return result;
    }

    public static<T> ResVO<T> result(Boolean b){
        return result(b, null);
    }


    public static<T> ResVO<T> result(Boolean b, String str){
        return result(b, str, null);
    }

    public static<T> ResVO<T> result(Boolean b, String str, T data){
        Integer code = b ? ResCodeEnum.SUCCESS.getCode() : ResCodeEnum.FAIL.getCode();
        String mes = b ? ResCodeEnum.SUCCESS.getMessage() : ResCodeEnum.FAIL.getMessage();
        ResVO<T> result = new ResVO<>();
        result.setCode(code);
        result.setMessage(StringUtils.isEmpty(str) ? mes : str);
        result.setData(data);
        return result;
    }

    public ResVO<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ResVO<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}