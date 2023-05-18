package com.zzx.zzx_music_recommendation_system.algorithm;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.Recommend1Dao;
import com.zzx.zzx_music_recommendation_system.dao.RecommendDao;
import com.zzx.zzx_music_recommendation_system.entity.*;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.service.*;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.utils.GetTrainAndTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    @Autowired
    private RecommendDao recommendDao;

    @Autowired
    private Recommend1Dao recommend1Dao;

    //最近邻用户个数
    private static final int k = 2;

    /**
     * 算法使用选择
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void recommend() {
        boolean chooseRecommend;
        if (!recommendDao.isExistRecommends()) {
            chooseRecommend = true;
        } else {
            if (!recommend1Dao.isExistRecommends()) {
                chooseRecommend = false;
            } else {
                recommendDao.remove(new QueryWrapper<Recommend>().lambda().eq(Recommend::getIsDelete, 1));
                recommend1Dao.remove(new QueryWrapper<Recommend1>().lambda().eq(Recommend1::getIsDelete, 1));
                chooseRecommend = true;
            }
        }
        //内容推荐
        Map<Long, List<Long>> contentMap = content();
        //knn
        knn(contentMap);
        //对contentMap保存
        if (chooseRecommend) {
            saveBatchRecommend(contentMap);
            recommend1Dao.remove(new QueryWrapper<Recommend1>().lambda().eq(Recommend1::getIsDelete, 1));
        } else {
            saveBatchRecommend1(contentMap);
            recommendDao.remove(new QueryWrapper<Recommend>().lambda().eq(Recommend::getIsDelete, 1));
        }
    }

    private void saveBatchRecommend(Map<Long, List<Long>> contentMap) {
        LocalDateTime now = LocalDateTime.now();
        List<Recommend> recommendList = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : contentMap.entrySet()) {
            if (recommendList.size() >= 2000) {
                //保存数据库
                recommendDao.saveBatch(recommendList);
                recommendList.clear();
            }
            for (Long musicId : entry.getValue()) {
                Recommend recommend = new Recommend();
                recommend.setUserId(entry.getKey());
                recommend.setMusicId(musicId);
                recommend.setGmtCreated(now);
                recommend.setGmtModified(now);
                recommend.setIsDelete(1);
                recommendList.add(recommend);
            }
        }
        if (recommendList.size() != 0) {
            //保存数据库
            recommendDao.saveBatch(recommendList);
            recommendList.clear();
        }
    }

    private void saveBatchRecommend1(Map<Long, List<Long>> contentMap) {
        LocalDateTime now = LocalDateTime.now();
        List<Recommend1> recommendList = new ArrayList<>();
        for (Map.Entry<Long, List<Long>> entry : contentMap.entrySet()) {
            if (recommendList.size() >= 2000) {
                //保存数据库
                recommend1Dao.saveBatch(recommendList);
                recommendList.clear();
            }
            for (Long musicId : entry.getValue()) {
                Recommend1 recommend = new Recommend1();
                recommend.setUserId(entry.getKey());
                recommend.setMusicId(musicId);
                recommend.setGmtCreated(now);
                recommend.setGmtModified(now);
                recommend.setIsDelete(1);
                recommendList.add(recommend);
            }
        }
        if (recommendList.size() != 0) {
            //保存数据库
            recommend1Dao.saveBatch(recommendList);
            recommendList.clear();
        }
    }

    public void knn(Map<Long, List<Long>> contentMap) {
        //取数据
        //有音乐操作记录的用户
        List<Long> userIdList = likeInfoService.list().stream()
                .map(LikeInfo::getUserId).distinct().collect(toList());
        //音乐集合
        List<Long> songIdList = likeInfoService.list().stream()
                .map(LikeInfo::getMusicId).distinct().collect(toList());
        List<LikeInfo> list = likeInfoService.list();
        List<LikeInfo> downloadList = list.stream().filter(l -> SongListTypeEnum.DOWNLOAD.getCode().equals(l.getLikeType())).collect(toList());
        List<LikeInfo> playList = list.stream().filter(l -> SongListTypeEnum.PLAY.getCode().equals(l.getLikeType())).collect(toList());
        List<LikeInfo> collectionList = list.stream().filter(l -> SongListTypeEnum.LIKE.getCode().equals(l.getLikeType())).collect(toList());
        //构建矩阵
        Map<Long,Map<Long, Float>> resMap = ScoreMatrixConstruction.getFrequencyMatrix(userIdList, songIdList, downloadList, playList, collectionList);
        //knn推荐
        Map<Long, Long[]> neighbours = UserKnn.getNearestNeighbor(userIdList, resMap, k);

        for (Map.Entry<Long,Map<Long, Float>> userMusicsMap : resMap.entrySet()) {
            Long userId = userMusicsMap.getKey();
            Map<Long, Float> musicMap = userMusicsMap.getValue();
            PriorityQueue<UserLikeScore> pq = new PriorityQueue<>((o1, o2) -> CommonUtils.compare(o1.score, o2.score));
            Long[] neighbour = neighbours.get(userId);
            for (Long musicId : songIdList) {
                UserLikeScore userLikeScore = new UserLikeScore();
                userLikeScore.musicId = musicId;
                if (musicMap.containsKey(musicId)) {
                    userLikeScore.score = musicMap.get(musicId);
                } else {
                    userLikeScore.score = GetTrainAndTestUtils.getPredictedRating(resMap, neighbour, musicId);
                }
                pq.add(userLikeScore);
            }

            //
            List<Long> userLikesByKNN = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                if (pq.peek() == null) {
                    break;
                }
                userLikesByKNN.add(pq.poll().musicId);
            }
            if (userLikesByKNN.size() == 0) {
                break;
            }

            //加入contentMap，将推荐结果保存
            if (contentMap.containsKey(userId)) {
                contentMap.put(userId, getHybrid(userLikesByKNN, contentMap.get(userId)));
            } else {
                contentMap.put(userId, userLikesByKNN);
            }
        }

    }

    private List<Long> getHybrid(List<Long> userLikesByKNN, List<Long> userLikesByContent) {
        Set<Long> resSet = new HashSet<>();
        Set<Long> commonSet = new HashSet<>(userLikesByKNN);
        commonSet.addAll(userLikesByContent);
        if (commonSet.size() <= 20) {
            return new ArrayList<>(commonSet);
        }
        int knnIdx = 0;
        int comIdx = 0;
        //先从内容推荐拿10个
        if (userLikesByContent.size() > 10) {
            resSet.addAll(userLikesByContent.subList(0, 10));
            comIdx = 10;
        } else {
            resSet.addAll(userLikesByContent);
            comIdx = userLikesByContent.size();
        }
        //再从knn拿10个
        if (userLikesByKNN.size() > 10) {
            resSet.addAll(userLikesByKNN.subList(0, 10));
            knnIdx = 10;
        } else {
            resSet.addAll(userLikesByKNN);
            knnIdx = userLikesByKNN.size();
        }
        //不够先从内容继续拿，没有从knn拿
        if (resSet.size() < 20) {
            for (int i = comIdx; i < userLikesByContent.size(); i++) {
                if (resSet.size() >= 20) {
                    return new ArrayList<>(resSet);
                }
                resSet.add(userLikesByContent.get(i));
            }
            for (int i = knnIdx; i < userLikesByKNN.size(); i++) {
                if (resSet.size() >= 20) {
                    return new ArrayList<>(resSet);
                }
                resSet.add(userLikesByKNN.get(i));
            }
        }
        return new ArrayList<>(resSet);
    }

    static class UserLikeScore {
        Long musicId;
        Float score;
    }


    public Map<Long, List<Long>> content() {
        //取数据
        Map<Long, Set<Long>> userTypes = userTypeService.list().stream()
                .collect(groupingBy(UserType::getUserId, mapping(UserType::getTypeId, toSet())));
        List<MusicInfo> songList = musicInfoService.list(new QueryWrapper<MusicInfo>().lambda().isNotNull(MusicInfo::getTypeIds));
        Map<Long, Set<Long>> musicTypes = new HashMap<>();
        for (MusicInfo m : songList) {
            String[] strings = m.getTypeIds().split("\\|");
            Set<Long> set = new HashSet();
            for (String s : strings) {
                if (NumberUtil.isNumber(s)) {
                    set.add(Long.valueOf(s));
                }
            }
            if (set.size() == 0) {
                continue;
            }
            musicTypes.put(m.getMusicId(), set);
        }

        //构建矩阵
        Map<Long, Map<Long, Float>> map = TypeScoreMatrixConstruction.getMatrix(userTypes, musicTypes);
        //推荐
        Map<Long, List<Long>> userRecommendMusics = new HashMap<>();
        for (Map.Entry<Long, Map<Long, Float>> entry : map.entrySet()) {
            List<Long> userRec = TypeScoreMatrixConstruction.contentBasedRecommend(map, 20, entry.getKey());
            userRecommendMusics.put(entry.getKey(), userRec);
        }
        return userRecommendMusics;
    }



}
