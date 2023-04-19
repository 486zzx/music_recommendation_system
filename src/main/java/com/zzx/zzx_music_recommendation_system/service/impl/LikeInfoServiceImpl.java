package com.zzx.zzx_music_recommendation_system.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.mapper.LikeInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.DateUtils;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class LikeInfoServiceImpl extends ServiceImpl<LikeInfoMapper, LikeInfo> implements LikeInfoService {

    @Autowired
    private LikeInfoDao likeInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean updateMouthRank() {
        LocalDateTime time = LocalDateTime.now().minusMonths(1);
        try {
            List<String> mouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), new TypeReference<List<String>>() {});
            if (mouth != null && mouth.size() >= 1) {
                return true;
            }
            List<String> lastMouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time.minusMonths(1)), new TypeReference<List<String>>() {});
            if (lastMouth != null && lastMouth.size() >= 1) {
                redisUtils.deleteList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time.minusMonths(1)));
            }
            List<String> list = likeInfoDao.updateMouthRank(DateUtils.getStartTime(time), DateUtils.getEndTime(time));
            redisUtils.setList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), list);
        } catch (Exception e) {
            log.error("redis出错", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateDayRank() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        try {
            List<String> day = redisUtils.getList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time), new TypeReference<List<String>>() {});
            if (day != null && day.size() >= 1) {
                return true;
            }
            List<String> lastDay = redisUtils.getList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time.minusMonths(1)), new TypeReference<List<String>>() {});
            if (lastDay != null && lastDay.size() >= 1) {
                redisUtils.deleteList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time.minusDays(1)));
            }
            List<String> list = likeInfoDao.updateDayRank(DateUtils.getStartTime(time), DateUtils.getEndTime(time));
            redisUtils.setList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time), list);
        } catch (Exception e) {
            log.error("redis出错", e);
            return false;
        }
        return true;
    }
}
