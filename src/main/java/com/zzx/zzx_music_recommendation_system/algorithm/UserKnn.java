package com.zzx.zzx_music_recommendation_system.algorithm;

import com.zzx.zzx_music_recommendation_system.vo.UserSimilarity;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @DESCRIPTION: 基于用户协同过滤 KNN
 * @USER: zzx
 * @DATE: 2023/3/31 18:30
 */
public class UserKnn {

    public static void getKnn(List<Long> userIdList, final Map<Long, float[]> user2songRatingMatrix, final int k) {
        Map<Long, Long[]> nearNeighbor = getNearestNeighbor(userIdList, user2songRatingMatrix, k);
        //预测评分（根据最近k个邻居，并且邻居同样打分的音乐评分来预测当前用户对歌曲的评分）
        user2songRatingMatrix.forEach(new BiConsumer<Long, float[]>() {
            @Override
            public void accept(Long aLong, float[] floats) {
                for (int i = 0; i < floats.length; i++) {
                    if (floats[i] > 0.01f) {
                        continue;
                    }
                    BigDecimal score = new BigDecimal("0");
                    int times = 0;
                    Long[] neighbors = nearNeighbor.get(aLong);
                    for (Long neighbor : neighbors) {
                        float nowScore;
                        //邻居有评分的话继续
                        if (Objects.nonNull(neighbor) && (nowScore = user2songRatingMatrix.get(neighbor)[i]) > 0.01f) {
                            times++;
                            //加上邻居对这首歌的评分
                            score = score.add(new BigDecimal(String.valueOf(nowScore)));
                        }
                    }
                    floats[i] = score.divide(new BigDecimal(String.valueOf(times))).floatValue();
                }
            }
        });

    }

    private static Map<Long, Long[]> getNearestNeighbor(List<Long> userIdList, final Map<Long, float[]> user2songRatingMatrix, final int k) {
        // TODO Auto-generated method stub
        final Map<Long,Long[]> userKNNMatrix= new HashMap<>();
        userIdList.forEach(new Consumer<Long>() {

            public void accept(final Long curUserId) {
                // TODO Auto-generated method stub
                Long[] knnId=new Long[k];
                //为用户建立一个最小堆来存放相似性最大的k个邻居，相似度越大，评分越低
                final PriorityQueue<UserSimilarity> minNumHeap=new PriorityQueue<>(k, new Comparator<UserSimilarity>() {
                    @Override
                    public int compare(UserSimilarity o1, UserSimilarity o2) {
                        return o1.getSimilarity().compareTo(o2.getSimilarity());
                    }
                });
                //获取K Nearest Neighbors
                user2songRatingMatrix.forEach(new BiConsumer<Long, float[]>() {

                    public void accept(Long otherUserId, float[] userRatingArray) {
                        // TODO Auto-generated method stub
                        //排除自己
                        if(otherUserId!=curUserId) {
                            //计算当前用户和其他用户的相似性
                            float similarity = calculateSimilarity(user2songRatingMatrix.get(curUserId),user2songRatingMatrix.get(otherUserId));
                            UserSimilarity userSimilarity = new UserSimilarity();
                            userSimilarity.setUserId(otherUserId);
                            userSimilarity.setSimilarity(similarity);
                            //放入堆中
                            minNumHeap.add(userSimilarity);
                        }

                    }

                });
                //从堆中获取相似性最大的k的邻居
                for(int i=0;i<k;i++) {
                    UserSimilarity temp = minNumHeap.poll();
                    if (Objects.isNull(temp)) {
                        break;
                    }
                    knnId[i]=temp.getUserId();
                }
                userKNNMatrix.put(curUserId, knnId);
            }

        });
        return userKNNMatrix;
    }



    /**
     * 计算相似度
     * @param curRating
     * @param otherRating
     * @return
     */
    private static float calculateSimilarity(float[] curRating, float[] otherRating) {
        // TODO Auto-generated method stub
        float similarity=0f;
        int len=curRating.length;
        int cnt=0;
        for(int i=0;i<len;i++) {
            //根据被当前用户“评分”了的项目来计算相似度，由于浮点数不能==比较，这里用0.01f表示0.0f
            if(curRating[i]>0.01f) {
                similarity+=Math.pow(curRating[i]-otherRating[i], 2);
                cnt++;
            }
        }
        similarity/=(cnt>0?cnt:1);

        return similarity;
    }
}
