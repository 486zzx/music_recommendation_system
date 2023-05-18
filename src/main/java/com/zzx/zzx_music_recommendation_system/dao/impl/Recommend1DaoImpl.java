package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.Recommend1Dao;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.entity.Recommend1;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.Recommend1Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/18 20:26
 */
@Service
public class Recommend1DaoImpl extends ServiceImpl<Recommend1Mapper, Recommend1> implements Recommend1Dao {

    @Override
    public boolean isExistRecommends() {
        List<Recommend1> list = list();
        return list != null && list.size() != 0;
    }

    @Override
    public List<String> getMusicIdsByRecommend() {
        List<Recommend1> recommends = list(new QueryWrapper<Recommend1>().lambda()
                .eq(Recommend1::getUserId, UserInfoUtil.getUserId()));
        if (recommends == null) {
            return new ArrayList<>();
        }
        return recommends.stream()
                .map(l -> Long.toString(l.getMusicId()))
                .collect(Collectors.toList());
    }

}
