package com.zzx.zzx_music_recommendation_system.service.impl;

import com.zzx.zzx_music_recommendation_system.dao.MusicTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.mapper.MusicTypeMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class MusicTypeServiceImpl extends ServiceImpl<MusicTypeMapper, MusicType> implements MusicTypeService {

    @Autowired
    private MusicTypeDao musicTypeDao;

    @Override
    public List<MusicType> getMusicType() {
        return musicTypeDao.list();
    }
}
