package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
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


}
