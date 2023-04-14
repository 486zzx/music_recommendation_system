package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 22:58
 */
@Data
@NoArgsConstructor
public class RegisterReqVO {

    @JSONField(format = "trim")
    private String userName;

    @JSONField(format = "trim")
    private String userEmail;

    @JSONField(format = "trim")
    private String userPassword;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
