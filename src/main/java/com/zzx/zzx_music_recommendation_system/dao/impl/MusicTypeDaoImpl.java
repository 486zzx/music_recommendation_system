package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.MusicTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.mapper.MusicTypeMapper;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:43
 */
@Service
public class MusicTypeDaoImpl extends ServiceImpl<MusicTypeMapper, MusicType> implements MusicTypeDao {
}
