package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zzx.zzx_music_recommendation_system.algorithm.RecommendAlgor;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.RecommendDao;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.RecommendMapper;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.service.UserTypeService;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.utils.DateUtils;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
 * @since 2023-04-04
 */
@Service
public class RecommendServiceImpl extends ServiceImpl<RecommendMapper, Recommend> implements RecommendService {

    @Autowired
    private RecommendDao recommenndDao;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private RecommendAlgor recommendAlgor;

    @Autowired
    private LikeInfoDao likeInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void updateRecommend() {
        recommenndDao.remove(new QueryWrapper<Recommend>().lambda()
                .eq(Recommend::getIsDelete, 1));
        Map<Long, Map<Long, Float>> map = recommendAlgor.recommend();
        List<Recommend> recommendList = new ArrayList<>();
        map.forEach((userId, map1) -> {
            map1.forEach((musicId, score) -> {
                Recommend recommend = new Recommend();
                recommend.setUserId(userId);
                recommend.setMusicId(musicId);
                recommend.setSocre(score);
                CommonUtils.fillWhenSaveNoLogin(recommend);
                recommendList.add(recommend);
            });
        });
        recommenndDao.saveBatch(recommendList);
    }

    @Override
    public List<Long> getRecommendMusic() {
        List<Long> list = recommenndDao.getMusicIdsByRecommend();
        //不够从月榜补充
        if (list.size() < 20) {
            LocalDateTime time = LocalDateTime.now().minusMonths(1);
            List<String> mouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), new TypeReference<List<String>>() {});
            int i = 0;
            while (list.size() < 20 && i < mouth.size()) {
                Long musicId = Long.parseLong(mouth.get(i));
                if (!list.contains(musicId)) {
                    list.add(musicId);
                }
                i++;
            }
        }
        return list;
    }
}
