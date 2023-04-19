package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.mapper.MusicInfoMapper;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:42
 */
@Service
public class MusicInfoDaoImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements MusicInfoDao {

    @Autowired
    private MusicInfoMapper musicInfoMapper;

    @Override
    public List<RankResVO> getMusicInfo(List<Long> musics) {
        return musicInfoMapper.getRank(musics);
    }
}
