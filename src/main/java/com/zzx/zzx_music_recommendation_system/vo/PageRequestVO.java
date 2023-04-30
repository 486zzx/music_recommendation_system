package com.zzx.zzx_music_recommendation_system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/25 16:50
 */
@Data
public class PageRequestVO {
    public PageRequestVO() {
        this.current = 1L;
        this.size = 20L;
    }

    @ApiModelProperty("当前页数")
    private Long current;
    @ApiModelProperty("每页条数")
    private Long size;
}
