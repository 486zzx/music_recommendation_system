package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/1 1:54
 */
@Data
public class GetMusicsFromSongListReqVO extends PageRequestVO{

    private Long songListId;
}
