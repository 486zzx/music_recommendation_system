package com.zzx.zzx_music_recommendation_system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 17:45
 */
@ApiModel("获取验证码reqVO")
@Data
public class ValidateCodeReqVO {

    @NotBlank(message = "邮箱不能为空！")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
}
