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
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_type")
    private Integer userType;

    @TableField("user_email")
    private String userEmail;

    @TableField("user_password")
    private String userPassword;

    @TableField("user_number")
    private String userNumber;

    @TableField("user_phone")
    private Integer userPhone;

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