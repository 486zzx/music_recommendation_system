package com.zzx.zzx_music_recommendation_system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("music_info")
@ApiModel(value = "MusicInfo对象", description = "")
public class MusicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("music_id")
    private Long musicId;

    @TableField("music_name")
    private String musicName;

    @TableField("music_type")
    private Integer musicType;

    @TableField("singer_id")
    private Long singerId;

    @TableField("type_ids")
    private String typeIds;

    @TableField("listen_times")
    private Long listenTimes;

    @TableField("music_content")
    private String musicContent;

    @TableField("trend_coefficient")
    private Integer trendCoefficient;

    @TableField("create_user_id")
    private Long createUserId;

    @TableField("modify_user_id")
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
    @TableLogic
    private Integer isDelete;


}
