package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;

import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:37
 */
public interface MusicInfoDao extends IService<MusicInfo> {

    List<RankResVO> getMusicInfo(List<Long> musics);

    IPage<RankResVO> getMusicsPage(Page<RankResVO> page, List<Long> musics);


}
