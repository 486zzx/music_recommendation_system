package com.zzx.zzx_music_recommendation_system.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/21 18:22
 */
@Data
public class GetOrSetCommentReqVO {

    private Long commentId;

    @NotNull(message = "音乐id不能为空！")
    private Long musicId;

    private Long fatherId;

    @NotBlank(message = "评论内容不能为空！")
    @JSONField(format = "trim")
    private String value1;

}
