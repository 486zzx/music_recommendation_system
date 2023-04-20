package com.zzx.zzx_music_recommendation_system.algorithm;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.service.SongListService;
import com.zzx.zzx_music_recommendation_system.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
    private SongListService songListService;

    @Autowired
    private LikeInfoService likeInfoService;

    //最近邻用户个数
    private static final int k = 2;


    /**
     * 算法使用选择
     * @param knn
     * @param alg2
     */
    public Map<Long, Map<Long, Float>> recommend(boolean knn, boolean alg2) {
        //构建评分矩阵

//        ScoreMatrixConstruction.getFrequencyMatrix();
        return null;
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
        List<Long> userIdList = userInfoService.list().stream().map(UserInfo::getUserId).collect(Collectors.toList());
        List<Long> songIdList = songListService.list().stream().map(SongList::getUserId).collect(Collectors.toList());
        List<LikeInfo> list = likeInfoService.list();
        List<LikeInfo> downloadList = list.stream().filter(l -> SongListTypeEnum.DOWNLOAD.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        List<LikeInfo> playList = list.stream().filter(l -> SongListTypeEnum.PLAY.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        List<LikeInfo> collectionList = list.stream().filter(l -> SongListTypeEnum.LIKE.getCode().equals(l.getLikeType())).collect(Collectors.toList());
        //构建矩阵
        Map<Long,Map<Long, Float>> resMap = ScoreMatrixConstruction.getFrequencyMatrix(userIdList, songIdList, downloadList, playList, collectionList);
        //
        UserKnn.getKnn(userIdList, resMap, k);
        return resMap;
    }



}
