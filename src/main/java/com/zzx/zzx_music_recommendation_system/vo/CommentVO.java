package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/20 1:33
 */
@Data
public class CommentVO {
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long commentId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long userId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long musicId;
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long fatherId;

    private Long createUserId;

    private Long modifyUserId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

    private List<CommentVO> commentVOS;


}
