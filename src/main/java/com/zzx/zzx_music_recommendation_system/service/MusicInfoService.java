package com.zzx.zzx_music_recommendation_system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.vo.*;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface MusicInfoService extends IService<MusicInfo> {

    List<RankResVO> getMusics(List<Long> musicIds);

    PageVO<RankResVO> getMusicsPage(Page<RankResVO> page, List<Long> musicIds);

    MusicDetailResVO musicDetail(Long musicId);

    List<RankResVO> getRandomMusic();

    List<RankResVO> getLastMusic();

    PageVO<CommentVO>  getComments(GetCommentsReqVO reqVO);

}
