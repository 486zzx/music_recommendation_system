package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.SingerInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.SingerInfo;
import com.zzx.zzx_music_recommendation_system.mapper.SingerInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.SingerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class SingerInfoServiceImpl extends ServiceImpl<SingerInfoMapper, SingerInfo> implements SingerInfoService {

    @Autowired
    private SingerInfoDao singerInfoDao;

    @Override
    public Map<String, String> getSingerName(List<String> singIds) {
        Map<String, String> res = new HashMap<>();
        List<Long> searchList = new ArrayList<>();
        singIds.forEach(l -> {
            if (StrUtil.isNumeric(l)) {
                searchList.add(Long.parseLong(l));
            } else {
                res.put(l, l);
            }
        });
        if (searchList.size() > 0) {
            Map<String, String> map = singerInfoDao.list(new QueryWrapper<SingerInfo>().lambda()
                            .in(SingerInfo::getSingerId, searchList))
                    .stream().collect(Collectors.toMap(l -> String.valueOf(l.getSingerId()), SingerInfo::getSingerName));
            res.putAll(map);
        }
        return res;
    }

}
