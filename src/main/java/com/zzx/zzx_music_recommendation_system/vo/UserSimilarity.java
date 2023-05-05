package com.zzx.zzx_music_recommendation_system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/3/31 18:49
 */
@Data
public class UserSimilarity {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private Float similarity;
}
