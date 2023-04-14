package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/14 22:18
 */
@Data
@Valid
public class ReqVO<T> {

    @Valid
    @NotNull(message = "参数为空！")
    private T args;

}
