package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.MusicSongListDao;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicSongList;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.MusicSongListMapper;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.service.MusicSongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
public class MusicSongListServiceImpl extends ServiceImpl<MusicSongListMapper, MusicSongList> implements MusicSongListService {

    @Autowired
    private MusicSongListDao musicSongListDao;

    @Autowired
    private SongListDao songListDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void addMusicToPlayList(List<Long> musicIds) {
        //取当前播放歌单的songListId
        SongList songList = songListDao.getFromNowPlay();
        Long songListId = songList.getSongListId();
        //查看已经存在的歌
        List<Long> existMusics = musicSongListDao.getExistSongs(songListId);
        musicIds.removeAll(existMusics);
        //插入当前播放列表
        List<MusicSongList> musicSongListList = existMusics.stream().map(l -> {
            MusicSongList musicSongList = new MusicSongList();
            musicSongList.setMusicId(l);
            musicSongList.setSongListId(songListId);
            CommonUtils.fillWhenSave(musicSongList);
            return musicSongList;
        }).collect(Collectors.toList());
        musicSongListDao.saveBatch(musicSongListList);
    }

    @Override
    public void deleteMusicFromPlayList(List<Long> musicIds) {
        //取当前播放歌单的songListId
        SongList songList = songListDao.getFromNowPlay();
        Long songListId = songList.getSongListId();
        //查看已经存在的歌
        List<Long> existMusics = musicSongListDao.getExistSongs(songListId);
        musicIds.removeAll(existMusics);
        musicSongListDao.removeByIds(musicIds);
    }
}
