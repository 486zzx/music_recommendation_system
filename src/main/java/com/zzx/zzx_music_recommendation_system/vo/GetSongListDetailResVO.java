package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/1 0:01
 */
@Data
public class GetSongListDetailResVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String userName;

    private String email;

    private Integer songListType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifyUserId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

    private Long collectNum;

}
