package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.algorithm.RecommendAlgor;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.RecommenndDao;
import com.zzx.zzx_music_recommendation_system.dao.UserTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.RecommendMapper;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.service.UserTypeService;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private RecommenndDao recommenndDao;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private RecommendAlgor recommendAlgor;

    @Autowired
    private LikeInfoDao likeInfoDao;

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
}
