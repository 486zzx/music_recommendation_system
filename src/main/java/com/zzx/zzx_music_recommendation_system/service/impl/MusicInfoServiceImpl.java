package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.mapper.MusicInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.vo.CommentVO;
import com.zzx.zzx_music_recommendation_system.vo.MusicDetailResVO;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements MusicInfoService {

    @Autowired
    private MusicInfoDao musicInfoDao;

    @Autowired
    private MusicTypeDao musicTypeDao;

    @Autowired
    private LikeInfoDao likeInfoDao;

    @Autowired
    private CommentInfoDao commentInfoDao;

    @Override
    public List<RankResVO> getRank(List<Long> musicIds) {
        List<RankResVO> resVOS = musicInfoDao.getMusicInfo(musicIds);
        Map<String, String> map = musicTypeDao.list().stream()
                .collect(Collectors.toMap(l -> Long.toString(l.getTypeId()), MusicType::getTypeName));
        resVOS.forEach(l -> {
            String[] strs = l.getTypeIds().split("\\|");;
            String[] typeNames = new String[strs.length];
            for (int i = 0; i < strs.length; i++) {
                typeNames[i] = map.get(strs[i]);
            }
            l.setTypeNames(typeNames);
        });
        return resVOS;
    }

    @Override
    public MusicDetailResVO musicDetail(Long musicId) {
        MusicDetailResVO resVO = new MusicDetailResVO();
        MusicInfo musicInfo = musicInfoDao.getById(musicId);
        BeanUtils.copyProperties(musicInfo, resVO);

        Map<String, String> map = musicTypeDao.list().stream()
                .collect(Collectors.toMap(l -> Long.toString(l.getTypeId()), MusicType::getTypeName));
        String[] strs = resVO.getTypeIds().split("\\|");;
        String[] typeNames = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            typeNames[i] = map.get(strs[i]);
        }
        resVO.setTypeNames(typeNames);

        Map<Integer, Long> map1 = likeInfoDao.getTypeCountMap(musicId);
        resVO.setPlayTimes(map1.get(SongListTypeEnum.PLAY.getCode()));
        resVO.setCollectTimes(map1.get(SongListTypeEnum.LIKE.getCode()));
        resVO.setDownloadTimes(map1.get(SongListTypeEnum.DOWNLOAD.getCode()));

        List<CommentInfo> commentInfos = commentInfoDao.list(new QueryWrapper<CommentInfo>().lambda()
                .eq(CommentInfo::getMusicId, musicId));
        List<CommentVO> commentVOS = BeanUtil.copyToList(commentInfos, CommentVO.class);
        //没有父节点的
        List<CommentVO> resCommentVOS = commentVOS.stream().filter(l -> l.getFatherId() == null).collect(Collectors.toList());
        commentVOS.removeAll(resCommentVOS);
        commentVOS.forEach(l -> {
            for (CommentVO vo : resCommentVOS) {
                if (vo.getCommentId().equals(l.getFatherId())) {
                    if (vo.getCommentVOS() == null) {
                        vo.setCommentVOS(new ArrayList<>());
                    }
                    vo.getCommentVOS().add(l);
                }
            }
        });
        resVO.setCommentVOS(resCommentVOS);
        return resVO;
    }

}

