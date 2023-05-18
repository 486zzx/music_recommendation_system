package com.zzx.zzx_music_recommendation_system.algorithm;

import java.util.*;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/21 17:21
 */
public class TypeScoreMatrixConstruction {

    /**
     * 构建用户-音乐评分矩阵
     * @param userLikeTypes 用户喜欢的标签
     * @param musicTypes 音乐对应的标签
     * @return
     */
    public static Map<Long, Map<Long, Float>> getMatrix(Map<Long, Set<Long>> userLikeTypes, Map<Long, Set<Long>> musicTypes) {
        Map<Long, Map<Long, Float>> ratingMatrix = new HashMap<>();

        for (Map.Entry<Long, Set<Long>> userEntry : userLikeTypes.entrySet()) {
            Long userId = userEntry.getKey();
            Set<Long> userLikes = userEntry.getValue();

            Map<Long, Float> userRatings = new HashMap<>();

            for (Map.Entry<Long, Set<Long>> musicEntry : musicTypes.entrySet()) {
                Long musicId = musicEntry.getKey();
                Set<Long> musicFeatures = musicEntry.getValue();

                // 计算用户对歌曲的评分，使用余弦相似度
                float similarity = calculateCosineSimilarity(userLikes, musicFeatures);

                userRatings.put(musicId, similarity);
            }

            ratingMatrix.put(userId, userRatings);
        }

        return ratingMatrix;
    }

    /**
     * 计算余弦相似度
     * @param set1 用户喜好标签
     * @param set2 音乐标签
     * @return
     */
    public static float calculateCosineSimilarity(Set<Long> set1, Set<Long> set2) {
        // 计算两个集合的交集数量
        Set<Long> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        int intersectionSize = intersection.size();
        if (set1.size() == 0 || set2.size() == 0) {
            return 0f;
        }
        // 计算两个集合的模长
        double magnitude1 = Math.sqrt(set1.size());
        double magnitude2 = Math.sqrt(set2.size());
        // 计算余弦相似度
        float cosineSimilarity = (float) (intersectionSize / (magnitude1 * magnitude2));

        return cosineSimilarity;
    }


    public static List<Long> contentBasedRecommend(Map<Long, Map<Long, Float>> matrix, int topN, Long userId) {
        Map<Long, Float> scores = matrix.get(userId);

        // 对得分进行排序，取前topN个音乐作为推荐结果，推荐结果需要有评分
        List<Long> recommendedMusicIds = new ArrayList<>();
        scores.entrySet().stream()
                .filter(longFloatEntry -> longFloatEntry.getValue() > 0.0f)
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(topN)
                .forEach(entry -> recommendedMusicIds.add(entry.getKey()));
        return recommendedMusicIds;
    }


    /**
     * 计算两首音乐的相似度
     * @param matrix 用户-音乐评分矩阵
     * @param musicId1 音乐1的ID
     * @param musicId2 音乐2的ID
     * @return 两首音乐的相似度
     */
    private static float similarity(Map<Long, Map<Long, Float>> matrix, Long musicId1, Long musicId2) {
        // 计算余弦相似度
        float dotProduct = 0.0f;
        float norm1 = 0.0f;
        float norm2 = 0.0f;
        for (Map<Long, Float> ratings : matrix.values()) {
            float rating1 = ratings.containsKey(musicId1) ? ratings.get(musicId1) : 0.0f;
            float rating2 = ratings.containsKey(musicId2) ? ratings.get(musicId2) : 0.0f;
            dotProduct += rating1 * rating2;
            norm1 += rating1 * rating1;
            norm2 += rating2 * rating2;
        }
        if (norm1 == 0.0f || norm2 == 0.0f) {
            return 0.0f;
        }
        return dotProduct / (float)(Math.sqrt(norm1) * Math.sqrt(norm2));
    }



}
