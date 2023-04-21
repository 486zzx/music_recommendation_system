package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.mapper.CommentInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.CommentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.vo.GetOrSetCommentReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    public void setOrUpdateComment(GetOrSetCommentReqVO reqVO) {
        CommentInfo commentInfo = new CommentInfo();
        BeanUtil.copyProperties(reqVO, commentInfo);
        if (Objects.isNull(commentInfo.getCommentId())) {
            CommonUtils.fillWhenSave(commentInfo);
            commentInfoDao.save(commentInfo);
        } else {
            CommonUtils.fillWhenUpdate(commentInfo);
            commentInfoDao.updateById(commentInfo);
        }
    }

    @Override
    public void deleteComment(Long commentId) {
        CommentInfo commentInfo = commentInfoDao.getById(commentId);
        if (commentInfo != null) {
            commentInfoDao.removeById(commentId);
        }
    }

}
