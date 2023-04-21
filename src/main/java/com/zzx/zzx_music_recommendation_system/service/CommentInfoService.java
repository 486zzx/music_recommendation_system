package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface CommentInfoService extends IService<CommentInfo> {

    void fillUserLikeType(List<Long> list);



}
