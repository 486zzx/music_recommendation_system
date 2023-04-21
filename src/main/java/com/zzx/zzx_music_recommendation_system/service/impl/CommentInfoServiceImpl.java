package com.zzx.zzx_music_recommendation_system.service.impl;

import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.mapper.CommentInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.CommentInfoService;
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
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {

    @Autowired
    private CommentInfoDao commentInfoDao;

    @Override
    public void fillUserLikeType(List<Long> list) {

    }


}
