package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 1:33
 */
@Data
public class CommentVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long musicId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fatherId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifyUserId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

    private String userValue1;

    private String userName;

//    private List<CommentVO> commentVOS;


}
