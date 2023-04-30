package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/25 16:54
 */
@Data
public class GetCommentsReqVO extends PageRequestVO {

    private Long musicId;

}
