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
 * @since 2023-04-03
 */
@Getter
@Setter
@TableName("user_type")
@ApiModel(value = "UserType对象", description = "")
public class UserType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_type_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userTypeId;

    @TableField("user_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @TableField("type_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;

    @TableField("score")
    private Integer score;

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
