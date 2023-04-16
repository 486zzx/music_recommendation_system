package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 15:36
 */
@ApiModel("登录reqVO")
@Data
public class LoginReqVO {

    @NotBlank(message = "邮箱不能为空！")
    @JSONField(format = "trim")
    private String userEmail;

    @NotBlank(message = "密码不能为空！")
    @JSONField(format = "trim")
    private String userPassword;


}
