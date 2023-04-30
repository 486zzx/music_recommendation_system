package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.zzx.zzx_music_recommendation_system.mapper.CommentInfoMapper;
import com.zzx.zzx_music_recommendation_system.vo.CommentVO;
import com.zzx.zzx_music_recommendation_system.vo.GetCommentsReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:40
 */
@Service
public class CommentInfoDaoImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoDao {

    @Autowired
    private CommentInfoMapper commentInfoMapper;

    @Override
    public IPage<CommentVO> queryForPage(GetCommentsReqVO musicId) {
        Page<CommentVO> page = new Page<>(musicId.getCurrent(), musicId.getSize());
        return commentInfoMapper.queryForPage(page, musicId.getMusicId());
    }
}
