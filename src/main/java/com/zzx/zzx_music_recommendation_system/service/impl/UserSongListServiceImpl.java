package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.dao.UserSongListDao;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.entity.UserSongList;
import com.zzx.zzx_music_recommendation_system.mapper.UserSongListMapper;
import com.zzx.zzx_music_recommendation_system.service.UserSongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.vo.GetHotSongListResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
public class UserSongListServiceImpl extends ServiceImpl<UserSongListMapper, UserSongList> implements UserSongListService {
    @Autowired
    private UserSongListDao userSongListDao;

    @Autowired
    private SongListDao songListDao;
    @Override
    public List<GetHotSongListResVO> getHotSongList() {
        Map<Long, Long> map = userSongListDao.getHotSongList();
        List<SongList> songLists = songListDao.list();
        List<GetHotSongListResVO> getHotSongListResVOS = BeanUtil.copyToList(songLists, GetHotSongListResVO.class);
        getHotSongListResVOS.forEach(l -> {
            l.setCollectNum(map.getOrDefault(l.getCollectNum(), 0L));
        });
        List<GetHotSongListResVO> res = getHotSongListResVOS.stream()
                .sorted(Comparator.comparingLong(GetHotSongListResVO::getCollectNum).reversed())
                .limit(20)
                .collect(Collectors.toList());
        return res;
    }
}
