package com.zzx.zzx_music_recommendation_system.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum SongListTypeEnum {
    LIKE(0,"收藏"),
    DOWNLOAD(1, "下载"),
    PLAY(2, "播放"),
    COMMON_SONG_LIST(3, "正常歌单"),
    NOW_PLAY(4, "当前播放"),
    ;

    private Integer code;

    private String message;

    SongListTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
