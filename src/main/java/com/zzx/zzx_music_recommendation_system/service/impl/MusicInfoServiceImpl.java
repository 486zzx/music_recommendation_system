package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.CommentInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.mapper.MusicInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.service.SingerInfoService;
import com.zzx.zzx_music_recommendation_system.utils.PageUtils;
import com.zzx.zzx_music_recommendation_system.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
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

    @Autowired
    private SingerInfoService singerInfoService;

    @Override
    public List<RankResVO> getMusics(List<Long> musicIds) {
        List<RankResVO> resVOS = musicInfoDao.getMusicInfo(musicIds);
        Map<String, String> singerMap = singerInfoService.getSingerName(resVOS.stream().map(RankResVO::getSingerId).collect(Collectors.toList()));
        resVOS.forEach(l -> {
            l.setSingerName(singerMap.get(l.getSingerId()));
        });
        Map<String, String> map = musicTypeDao.list().stream()
                .collect(Collectors.toMap(l -> Long.toString(l.getTypeId()), MusicType::getTypeName));
        resVOS.forEach(l -> {
            if (l.getTypeIds() == null) {
                return;
            }
            String[] strs = l.getTypeIds().split("\\|");;
            if (strs == null || strs.length == 0) {
                return;
            }
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

        if (StrUtil.isNumeric(resVO.getSingerId())) {
            String name = singerInfoService.getById(Long.parseLong(resVO.getSingerId())).getSingerName();
            resVO.setSingerName(name);
        } else {
            resVO.setSingerName(resVO.getSingerId());
        }

        if (StringUtils.hasText(resVO.getTypeIds())) {
            Map<String, String> map = musicTypeDao.list().stream()
                    .collect(Collectors.toMap(l -> Long.toString(l.getTypeId()), MusicType::getTypeName));
            String[] strs = resVO.getTypeIds().split("\\|");;
            if (strs != null || strs.length < 1) {
                String[] typeNames = new String[strs.length];
                for (int i = 0; i < strs.length; i++) {
                    typeNames[i] = map.get(strs[i]);
                }
                resVO.setTypeNames(typeNames);
            }
        }

        Map<Integer, Long> map1 = likeInfoDao.getTypeCountMap(musicId);
        resVO.setPlayTimes(map1.get(SongListTypeEnum.PLAY.getCode()));
        resVO.setCollectTimes(map1.get(SongListTypeEnum.LIKE.getCode()));
        resVO.setDownloadTimes(map1.get(SongListTypeEnum.DOWNLOAD.getCode()));

        //没有父节点的
//        List<CommentVO> resCommentVOS = commentVOS.stream().filter(l -> l.getFatherId() == null).collect(Collectors.toList());
//        commentVOS.removeAll(resCommentVOS);
//        commentVOS.forEach(l -> {
//            for (CommentVO vo : resCommentVOS) {
//                if (vo.getCommentId().equals(l.getFatherId())) {
//                    if (vo.getCommentVOS() == null) {
//                        vo.setCommentVOS(new ArrayList<>());
//                    }
//                    vo.getCommentVOS().add(l);
//                }
//            }
//        });
//        resVO.setCommentVOS(resCommentVOS);
        return resVO;
    }

    @Override
    public List<RankResVO> getRandomMusic() {
        List<MusicInfo> list = musicInfoDao.list();
        if (list == null || list.size() == 0) {
            return null;
        }
        Collections.shuffle(list);
        List<Long> selectedElements = list.subList(0, Math.min(list.size(), 20)).stream().map(MusicInfo::getMusicId).collect(Collectors.toList());
        return getMusics(selectedElements);
    }

    @Override
    public List<RankResVO> getLastMusic() {
        List<MusicInfo> list = musicInfoDao.list(new QueryWrapper<MusicInfo>().lambda()
                .orderByDesc(MusicInfo::getGmtCreated)
                .last(StringConstants.LIMIT_20));
        if (list == null || list.size() == 0) {
            return null;
        }
        List<Long> selectedElements = list.stream().map(MusicInfo::getMusicId).collect(Collectors.toList());
        return getMusics(selectedElements);
    }

    @Override
    public PageVO<CommentVO> getComments(GetCommentsReqVO reqVO) {
        PageVO<CommentVO> pageVO = PageUtils.getPageResVO(CommentVO.class, () -> commentInfoDao.queryForPage(reqVO));
        return pageVO;
    }

}

