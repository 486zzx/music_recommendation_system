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
 * @since 2023-04-04
 */
@Getter
@Setter
@TableName("recommend")
@ApiModel(value = "Recommend对象", description = "")
public class Recommend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("recommend_id")
    private Long recommendId;

    @TableField("user_id")
    private Long userId;

    @TableField("music_id")
    private Long musicId;

    @TableField("socre")
    private Float socre;

    @TableField("recommend_type")
    private Integer recommendType;

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
    @TableLogic(value="1",delval="0")
    private Integer isDelete;


}
