package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/4 14:18
 */
@Data
public class UserDetailResVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String name;

    private Integer userType;

    private String userEmail;

    private String userNumber;

    private Long userPhone;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

    private Integer playCount;
    //我创建的歌单信息
    private List<GetHotSongListResVO> createSongLists;
    //我喜欢的音乐id
    private List<String> musicInfoList;
    //我收藏的歌单
    private List<GetHotSongListResVO> collectSongLists;

}
