package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/22 1:19
 */
@Data
public class AddOrUpdateSongListReqVO {

    private Long songListId;

    @NotBlank(message = "歌单名称不能为空！")
    private String value1;



}
