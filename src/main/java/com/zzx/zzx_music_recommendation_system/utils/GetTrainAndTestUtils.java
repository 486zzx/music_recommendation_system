package com.zzx.zzx_music_recommendation_system.utils;

import com.zzx.zzx_music_recommendation_system.algorithm.ScoreMatrixConstruction;
import com.zzx.zzx_music_recommendation_system.algorithm.UserKnn;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/8 2:21
 */
public class GetTrainAndTestUtils {


    public static void main(String[] args) throws IOException {
        Set<Long> userIdSet = new HashSet<>();
        long idx = 1;
        Map<String, Long> musicMap = new HashMap<>();
        List<LikeInfo> playList = new ArrayList<>();

        File file = new File("src/main/resources/userid-timestamp-artid-artname-traid-traname.tsv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            // 处理每一行数据
            String[] strings = line.split("\t");
            String user = '1' + strings[0].substring(5);
            Long u = Long.parseLong(user);
            userIdSet.add(u);

            String musicNam = strings[5];
            long musicId;
            if ((musicId = musicMap.getOrDefault(musicNam, 0L)) == 0L) {
                //不存在
                musicId = idx++;
                musicMap.put(musicNam, musicId);

            }
            LikeInfo likeInfo = new LikeInfo();
            likeInfo.setUserId(u);
            likeInfo.setMusicId(musicId);
            playList.add(likeInfo);
        }
        // 关闭 BufferedReader 对象
        reader.close();

        Map<Long, List<LikeInfo>> userRatings = new HashMap<>();
        for (LikeInfo rating : playList) {
            long userId = rating.getUserId();
            if (!userRatings.containsKey(userId)) {
                userRatings.put(userId, new ArrayList<>());
            }
            userRatings.get(userId).add(rating);
        }

        // 分割训练集和测试集
        List<LikeInfo> trainSet = new ArrayList<>();
        List<LikeInfo> testSet = new ArrayList<>();
        for (List<LikeInfo> ratings : userRatings.values()) {
            int size = ratings.size();
            int cutoff = (int) Math.round(size * 0.8); // 80% 作为训练集
            for (int i = 0; i < size; i++) {
                LikeInfo rating = ratings.get(i);
                if (i < cutoff) {
                    trainSet.add(rating);
                } else {
                    testSet.add(rating);
                }
            }
        }


        Map<Long, Set<Long>> testUserIdToMusicId = new HashMap<>();
        for (LikeInfo test : testSet) {
            Set<Long> temp = new HashSet<>();
            //存在
            if (testUserIdToMusicId.containsKey(test.getUserId())) {
                temp = testUserIdToMusicId.get(test.getUserId());
            }
            temp.add(test.getMusicId());
            testUserIdToMusicId.put(test.getUserId(), temp);
        }

        List<Long> userIdList = new ArrayList<>(userIdSet);
        Map<Long, Map<Long, Float>> resMap = ScoreMatrixConstruction.getFrequencyMatrix(
                userIdList, new ArrayList<>(musicMap.values()), null, trainSet, null);
        Map<Long, Map<Long, Float>> testResMap = ScoreMatrixConstruction.getFrequencyMatrix(
                userIdList, new ArrayList<>(musicMap.values()), null, testSet, null);
        //推荐
        Map<Long, Long[]> map = UserKnn.getNearestNeighbor(userIdList, resMap, 5);
        System.out.println("不使用KNN预测时,RMSE:" + calculateRMSENotRecommend(resMap, testResMap, map));
        recommend(userIdList, new HashMap<>(resMap), testResMap, 5, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 20, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 50, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 100, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 200, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 500, 10);
        recommend(userIdList, new HashMap<>(resMap), testResMap, 800, 10);


    }

    /**
     * @param userIdList 用户集合
     * @param resMap     评分后的矩阵
     * @param testResMap 测试集评分后的矩阵
     * @param k1         k值
     * @param times      推荐的歌曲个数
     */
    public static void recommend(List<Long> userIdList, Map<Long, Map<Long, Float>> resMap, Map<Long, Map<Long, Float>> testResMap, int k1, int times) {
        Map<Long, Long[]> map = UserKnn.getNearestNeighbor(userIdList, resMap, k1);
//        PriorityQueue<Map.Entry<Long, Float>> pq = new PriorityQueue<>((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
//
//        //测试准确率
//        float res = 0f;
//        for (Map.Entry<Long,Map<Long, Float>> e : resMap.entrySet()) {
//            Long k = e.getKey();
//            Map<Long, Float> v = e.getValue();
//            float t = 0;
//            Set<Long> set = testUserIdToMusicId.get(k);
//            if (set == null) {
//                continue;
//            }
//            pq.addAll(v.entrySet());
//            for (int i = 0; i < times; i++) {
//                if (pq.peek() == null) {
//                    continue;
//                }
//                Map.Entry<Long, Float> entry = pq.poll();
//                if (set.contains(entry.getKey())) {
//                    t++;
//                }
//            }
//            res += t/times;
//        }
//        res = res / resMap.size();
        System.out.println("KNN算法:k=" + k1 + "时,RMSE:" + calculateRMSE(resMap, testResMap, map));
        //召回率

    }

    public static double calculateRMSE(Map<Long, Map<Long, Float>> resMap, Map<Long, Map<Long, Float>> testResMap, Map<Long, Long[]> map) {
        double sum = 0.0;
        int count = 0;
        for (Long userId : testResMap.keySet()) {
            Map<Long, Float> testRatings = testResMap.get(userId);
            Map<Long, Float> predictedRatings = resMap.get(userId);
            if (predictedRatings != null) {
                for (Long songId : testRatings.keySet()) {
                    Float predictedRating = predictedRatings.get(songId);
                    Float actualRating = testRatings.get(songId);
                    if (actualRating != null) {
                        if (predictedRating != null) {
                            sum += Math.pow(predictedRating - actualRating, 2);
                            count++;

                        } else {
                            predictedRating = getPredictedRating(resMap, map.get(userId), songId);
                            sum += Math.pow(predictedRating - actualRating, 2);
                            count++;
                        }
                    }

                }
            }
        }
        double rmse = Math.sqrt(sum / count);
        return rmse;
    }

    public static double calculateRMSENotRecommend(Map<Long, Map<Long, Float>> resMap, Map<Long, Map<Long, Float>> testResMap, Map<Long, Long[]> map) {
        double sum = 0.0;
        int count = 0;
        for (Long userId : testResMap.keySet()) {
            Map<Long, Float> testRatings = testResMap.get(userId);
            Map<Long, Float> predictedRatings = resMap.get(userId);
            if (predictedRatings != null) {
                for (Long songId : testRatings.keySet()) {
                    Float predictedRating = predictedRatings.get(songId);
                    Float actualRating = testRatings.get(songId);
                    if (actualRating != null) {
                        if (predictedRating != null) {
                            sum += Math.pow(predictedRating - actualRating, 2);
                            count++;

                        } else {
//                            predictedRating = getPredictedRating(resMap, map.get(userId), songId);
//                            sum += Math.pow(predictedRating - actualRating, 2);
//                            count++;
                        }
                    }

                }
            }
        }
        double rmse = Math.sqrt(sum / count);
        return rmse;
    }

    public static float getPredictedRating(Map<Long, Map<Long, Float>> resMap, Long[] neighbours, Long songId) {
        BigDecimal score = new BigDecimal("0");
        int times = 0;
        for (Long neighbour : neighbours) {
            if (Objects.isNull(neighbour)) {
                continue;
            }
            float nowScore;
            Map<Long, Float> map = resMap.get(neighbour);
            //邻居有评分的话继续
            if ((nowScore = map.getOrDefault(songId, 0f)) > 0.0f) {
                times++;
                //加上邻居对这首歌的评分
                score = score.add(new BigDecimal(String.valueOf(nowScore)));
            }
        }
        if (times != 0) {
            return score.divide(new BigDecimal(String.valueOf(times)), 9, RoundingMode.HALF_UP).floatValue();
        } else {
            return 0f;
        }
    }


}
