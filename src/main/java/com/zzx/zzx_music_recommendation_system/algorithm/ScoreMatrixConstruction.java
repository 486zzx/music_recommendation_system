package com.zzx.zzx_music_recommendation_system.algorithm;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * @DESCRIPTION: 评分矩阵构建
 * @USER: zzx
 * @DATE: 2023/3/31 17:55
 */
public class ScoreMatrixConstruction {

    private final static float PLAY_SCORE=1f;
    private final static float DOWNLOAD_SCORE=2f;
    private final static float COLLECTION_SCORE=4f;
    private final static float MAX_SCORE=10f;
    private final static long FLAG =0;

    private final static long PLAY_TIME=10;


    /**
     * 用用户行为进行隐式评分
     * 总分10分，播放10次得1分，下载2分，收藏4分，如果超过10分，按10分计算.
     * @param userIdList
     * 用户Id列表
     * @param songIdList
     * 歌曲Id列表
     * @param downloadList
     * 用户的下载记录列表
     * @param playList
     * 用户的播放记录列表
     * @param collectionList
     * 用户的收藏记录列表
     * @return
     * 用户Id-歌曲Id 频率矩阵
     */
    public static Map<Long,Map<Long, Float>> getFrequencyMatrix(List<Long> userIdList, List<Long> songIdList,
                                                           List<LikeInfo> downloadList, List<LikeInfo> playList, List<LikeInfo> collectionList) {
        // TODO Auto-generated method stub
        final Map<Long,Map<Long, Float>> user2songRatingMatrix=new HashMap<>();
        final int songLen=songIdList.size();
        //获取用户-歌曲 下载映射
        final Map<Long,Map<Long, Set<Long>>> userId2songIdDownloadMap=getUserId2songIdRecordMap(downloadList,false);
        //获取用户-歌曲 收藏映射
        final Map<Long, Map<Long, Set<Long>>> userId2songIdCollectionMap=getUserId2songIdRecordMap(collectionList,false);
        //获取用户-歌曲-次数 播放映射
        final Map<Long, Map<Long, Set<Long>>> userId2songIdPlayMap=getUserId2songIdRecordMap(playList,true);

        userIdList.forEach(userId -> {
            // TODO Auto-generated method stub
            Map<Long, Float> curUserRatingArray = new HashMap<>();
            //处理每一首歌曲
            for(Long songId:songIdList) {
                /**
                 * 处理下载，这里不考虑下载次数
                 */
                if(userId2songIdDownloadMap.get(userId)!=null && userId2songIdDownloadMap.get(userId).get(FLAG).contains(songId)) {
                    //当前用户下载过的歌曲
                    curUserRatingArray.put(songId, curUserRatingArray.getOrDefault(songId, 0f) + DOWNLOAD_SCORE);
                }

                /**
                 * 处理收藏，这里没有次数
                 */
                if(userId2songIdCollectionMap.get(userId)!=null && userId2songIdCollectionMap.get(userId).get(FLAG).contains(songId)) {
                    //当前用户收藏的歌曲
                    curUserRatingArray.put(songId, curUserRatingArray.getOrDefault(songId, 0f) + COLLECTION_SCORE);
                }

                /**
                 * 处理播放，考虑播放次数
                 */
                if(userId2songIdPlayMap.get(userId)!=null && userId2songIdPlayMap.get(userId).get(FLAG).contains(songId)) {
                    //表示分数
                    long socre=userId2songIdPlayMap.get(userId).get(songId).iterator().next() / PLAY_TIME;
                    curUserRatingArray.put(songId, curUserRatingArray.getOrDefault(songId, 0f) + PLAY_SCORE * socre);
                }

                /**
                 * 处理最大得分，超过最大得分，记为最大得分
                 */
                if(curUserRatingArray.getOrDefault(songId, 0f)>MAX_SCORE) {
                    curUserRatingArray.put(songId, MAX_SCORE);
                }
            }
            //处理完一个用户
            user2songRatingMatrix.put(userId, curUserRatingArray);
        });
        return user2songRatingMatrix;
    }

    /**
     * 获取用户Id - 歌曲Id 的映射Map
     * @param recordList
     * 包含userId，songId的记录列表
     * @param isCount
     * 是否需要计数。如果true，则Long[1]存放计数。
     * @return
     * 两层Map
     * 第一层Map<Long,Map> 每个userId拥有一个自己的Map：
     * userId,userSetMap
     *
     * 第二层Map<Long,Set> 用户自己的Map里面存放两个东西：
     * （1）需要计数的话，为每首歌曲计数songId,Set；
     * （2）存放出现过的歌曲Flay,Set<SongId>.
     */
    private static <T> Map<Long, Map<Long, Set<Long>>> getUserId2songIdRecordMap(final List<T> recordList,final boolean isCount) {
        // TODO Auto-generated method stub
        final Map<Long, Map<Long, Set<Long>>> userId2songIdRecordMap=new HashMap<>();

        recordList.forEach(new Consumer<T>() {

            public void accept(T t) {
                // TODO Auto-generated method stub
                try {
                    Field userIdField=t.getClass().getDeclaredField("userId");
                    Field songIdField=t.getClass().getDeclaredField("songId");
                    userIdField.setAccessible(true);
                    songIdField.setAccessible(true);
                    long userId=userIdField.getLong(t);
                    long songId=songIdField.getLong(t);

                    if(userId2songIdRecordMap.containsKey(userId)) {
                        //获取当前用户的记录集合Map
                        Map<Long,Set<Long>> curRecordSetMap=userId2songIdRecordMap.get(userId);
                        //将当前歌曲添加到当前用户的记录集合中
                        curRecordSetMap.get(FLAG).add(songId);
                    }else {
                        Map<Long,Set<Long>> curRecordSetMap= new HashMap<>();
                        //创建记录歌曲Id的集合
                        Set<Long> curSongIdSet=new HashSet<>();
                        curSongIdSet.add(songId);
                        curRecordSetMap.put(FLAG, curSongIdSet);
                        userId2songIdRecordMap.put(userId, curRecordSetMap);
                    }
                    if(isCount) {
                        count(songId,userId2songIdRecordMap.get(userId));
                    }


                }catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }



            private void count(long songId, Map<Long, Set<Long>> curRecordSetMap) {
                // TODO Auto-generated method stub
                /**
                 * 计数,如果Map<songId,count>已经存在，则直接计数+1
                 */
                if(curRecordSetMap.containsKey(songId)) {
                    //获取当前用户歌曲的计数集合(只有一个元素)
                    Set<Long> curCountSet=curRecordSetMap.get(songId);
                    long cnt=curCountSet.iterator().next()+1;
                    curCountSet.clear();
                    curCountSet.add(cnt);
                }else {
                    Set<Long> curCountSet=new HashSet<>();
                    curCountSet.add(1L);
                    curRecordSetMap.put(songId, curCountSet);
                }
            }


        });
        return userId2songIdRecordMap;
    }

}
