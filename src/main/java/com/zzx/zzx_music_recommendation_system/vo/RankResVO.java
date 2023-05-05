package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/19 12:38
 */
@Data
public class RankResVO implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long musicId;

    private String musicName;

    private Integer musicType;

    private String singerId;

    private String singerName;

    private String typeIds;

    //通过typeIds查找
    private String[] typeNames;

    private Long listenTimes;

    private String musicContent;

    private Integer trendCoefficient;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifyUserId;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    private String value1;

    private String value2;

    private String value3;

}
