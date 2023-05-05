package com.zzx.zzx_music_recommendation_system.algorithm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.entity.*;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/3/31 18:27
 */
@Component
public class RecommendAlgor {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private LikeInfoService likeInfoService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private MusicTypeService musicTypeService;

    //最近邻用户个数
    private static final int k = 2;


    /**
     * 算法使用选择
     */
    public Map<Long, Map<Long, Float>> recommend() {
        //构建评分矩阵

//        ScoreMatrixConstruction.getFrequencyMatrix();
        return new HashMap<>();
    }

    /**
     * 我想将两个二维矩阵加权混合,ratio表示混合加权时map1与map2的比值
     * @param map1
     * @param map2
     * @param ratio
     * @return
     */
    private Map<Long, Map<Long, Float>> mix(Map<Long, Map<Long, Float>> map1, Map<Long, Map<Long, Float>> map2, float ratio) {
        return null;
    }



    public Map<Long, Map<Long, Float>> knn() {
        //取数据
        //有音乐操作记录的用户
        List<Long> userIdList = likeInfoService.list().stream()
                .map(LikeInfo::getUserId).distinct().collect(Collectors.toList());
        //被操作过的音乐
        List<Long> songIdList = likeInfoService.list().stream()
                .map(LikeInfo::getMusicId).collect(Collectors.toList());
        List<LikeInfo> list = likeInfoService.list();
        List<LikeInfo> downloadList = list.stream().filter(l -> SongListTypeEnum.DOWNLOAD.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        List<LikeInfo> playList = list.stream().filter(l -> SongListTypeEnum.PLAY.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        List<LikeInfo> collectionList = list.stream().filter(l -> SongListTypeEnum.LIKE.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        //构建矩阵
        Map<Long,Map<Long, Float>> resMap = ScoreMatrixConstruction.getFrequencyMatrix(userIdList, songIdList, downloadList, playList, collectionList);
        //推荐
        UserKnn.getKnn(userIdList, resMap, k);
        return resMap;
    }


    public Map<Long, Map<Long, Float>> content() {
        //取数据
        //有喜好标签的用户
        List<Long> userIdList = userTypeService.list()
                .stream().map(UserType::getUserId).distinct().collect(Collectors.toList());
        //有标签的音乐
        List<Long> songIdList = musicInfoService.list(new QueryWrapper<MusicInfo>().lambda().isNotNull(MusicInfo::getTypeIds))
                .stream().map(MusicInfo::getMusicId).collect(Collectors.toList());
        List<LikeInfo> list = likeInfoService.list();

        //构建矩阵

        //推荐
        return null;
    }



}
