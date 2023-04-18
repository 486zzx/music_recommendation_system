package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.mapper.CommentInfoMapper;
import com.zzx.zzx_music_recommendation_system.mapper.SongListMapper;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:40
 */
@Service
public class CommentInfoDaoImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoDao {
}
