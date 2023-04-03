package com.zzx.zzx_music_recommendation_system.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/3/31 18:49
 */
@Data
public class UserSimilarity {

    private Long userId;

    private Float similarity;
}
