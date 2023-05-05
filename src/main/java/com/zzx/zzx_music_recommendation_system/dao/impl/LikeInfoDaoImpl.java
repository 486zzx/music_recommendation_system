package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.mapper.LikeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:41
 */
@Service
public class LikeInfoDaoImpl extends ServiceImpl<LikeInfoMapper, LikeInfo> implements LikeInfoDao {

    @Autowired
    private LikeInfoMapper likeInfoMapper;

    @Override
    public List<String> updateMouthRank(LocalDateTime startTime, LocalDateTime endTime){
        List<String> list = likeInfoMapper.updateMouthRank(startTime, endTime);
        return list;
    }

    @Override
    public List<String> updateDayRank(LocalDateTime startTime, LocalDateTime endTime) {
        List<String> list = likeInfoMapper.updateMouthRank(startTime, endTime);
        return list;
    }

    @Override
    public Map<Integer, Long> getTypeCountMap(Long musicId) {
        return list(new QueryWrapper<LikeInfo>().lambda()
                        .eq(LikeInfo::getMusicId, musicId))
                .stream()
                .collect(Collectors.groupingBy(LikeInfo::getLikeType, Collectors.counting()));
    }

    @Override
    public Integer getPlayCount(Long userId) {
        Integer res = likeInfoMapper.selectCount(new QueryWrapper<LikeInfo>().lambda()
                .eq(LikeInfo::getUserId, userId)
                .eq(LikeInfo::getLikeType, SongListTypeEnum.PLAY.getCode()));
        return res;

    }

    @Override
    public List<LikeInfo> getCollectSongs(Long userId) {
        List<LikeInfo> res = likeInfoMapper.selectList(new QueryWrapper<LikeInfo>().lambda()
                .eq(LikeInfo::getUserId, userId)
                .eq(LikeInfo::getLikeType, SongListTypeEnum.LIKE.getCode()));
        return res;
    }

    @Override
    public Integer getMusicPlayCount(Long musicId) {
        Integer res = likeInfoMapper.selectCount(new QueryWrapper<LikeInfo>().lambda()
                .eq(LikeInfo::getMusicId, musicId)
                .eq(LikeInfo::getLikeType, SongListTypeEnum.PLAY.getCode()));
        return res;
    }


}
