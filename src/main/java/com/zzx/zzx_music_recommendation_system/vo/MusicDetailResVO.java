package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;

import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 1:13
 */
@Data
public class MusicDetailResVO extends RankResVO{

    private Long playTimes;
    private Long collectTimes;
    private Long downloadTimes;

    private List<CommentVO> commentVOS;

}
