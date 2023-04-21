package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.RecommendDao;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.RecommendMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:44
 */
@Service
public class RecommendDaoImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendDao {

    @Override
    public List<Long> getMusicIdsByRecommend() {
        return list(new QueryWrapper<Recommend>().lambda()
                .eq(Recommend::getUserId, UserInfoUtil.getUserId())
                .orderByDesc()
                .last(StringConstants.LIMIT_20))
                .stream()
                .map(Recommend::getMusicId)
                .collect(Collectors.toList());

    }

}
