package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/22 2:29
 */
@Data
public class AddOrDeleteMusicToSongListReqVO {

    private List<Long> songListIds;

    private List<Long> musicIds;
}