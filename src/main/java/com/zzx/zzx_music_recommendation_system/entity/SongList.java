package com.zzx.zzx_music_recommendation_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Getter
@Setter
@TableName("song_list")
@ApiModel(value = "SongList对象", description = "")
public class SongList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("song_list_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField("song_list_type")
    private Integer songListType;

    @TableField("create_user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUserId;

    @TableField("modify_user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long modifyUserId;

    @TableField("gmt_created")
    private LocalDateTime gmtCreated;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    @TableField("value1")
    private String value1;

    @TableField("value2")
    private String value2;

    @TableField("value3")
    private String value3;

    @TableField("is_delete")
    @TableLogic(value="1",delval="0")
    private Integer isDelete;


}
