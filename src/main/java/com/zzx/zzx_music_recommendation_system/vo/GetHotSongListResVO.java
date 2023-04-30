package com.zzx.zzx_music_recommendation_system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/28 20:55
 */
@Data
public class GetHotSongListResVO {

    private Long songListId;

    private Long userId;

    private Integer songListType;

    private Long createUserId;

    private Long modifyUserId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

    private Long collectNum;

}
