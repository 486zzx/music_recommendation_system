package com.zzx.zzx_music_recommendation_system.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.dao.MusicSongListDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicSongList;
import com.zzx.zzx_music_recommendation_system.mapper.MusicSongListMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 15:43
 */
@Service
public class MusicSongListDaoImpl extends ServiceImpl<MusicSongListMapper, MusicSongList> implements MusicSongListDao {


    @Override
    public List<Long> getExistSongs(Long songListId) {
        return list(new QueryWrapper<MusicSongList>().lambda()
                .eq(MusicSongList::getSongListId, songListId))
                .stream().map(MusicSongList::getMusicId).collect(Collectors.toList());
    }
}
