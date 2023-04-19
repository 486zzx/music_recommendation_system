package com.zzx.zzx_music_recommendation_system.service.impl;

import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.mapper.MusicInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MusicInfoServiceImpl extends ServiceImpl<MusicInfoMapper, MusicInfo> implements MusicInfoService {

    @Autowired
    private MusicInfoDao musicInfoDao;

    @Autowired
    private MusicTypeDao musicTypeDao;

    @Override
    public List<RankResVO> getRank(List<Long> musicIds) {
        List<RankResVO> resVOS = musicInfoDao.getMusicInfo(musicIds);
        Map<String, String> map = musicTypeDao.list().stream()
                .collect(Collectors.toMap(l -> Long.toString(l.getTypeId()), MusicType::getTypeName));
        resVOS.forEach(l -> {
            String[] strs = l.getTypeIds().split("\\|");;
            String[] typeNames = new String[strs.length];
            for (int i = 0; i < strs.length; i++) {
                typeNames[i] = map.get(strs[i]);
            }
            l.setTypeNames(typeNames);
        });
        return resVOS;
    }
}
