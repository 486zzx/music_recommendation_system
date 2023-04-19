package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.SingerInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.SingerInfo;
import com.zzx.zzx_music_recommendation_system.mapper.SingerInfoMapper;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:45
 */
@Service
public class SingerInfoDaoImpl extends ServiceImpl<SingerInfoMapper, SingerInfo> implements SingerInfoDao {
}
