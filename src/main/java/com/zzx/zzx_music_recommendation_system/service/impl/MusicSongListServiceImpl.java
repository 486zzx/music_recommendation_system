package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.MusicSongListDao;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicSongList;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.mapper.MusicSongListMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicSongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.vo.AddOrDeleteMusicToSongListReqVO;
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
        List<MusicSongList> musicSongListList = musicIds.stream().map(l -> {
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

    @Override
    public List<Long> getPlayList() {
        SongList songList = songListDao.getFromNowPlay();
        Long songListId = songList.getSongListId();

        return musicSongListDao.list(new QueryWrapper<MusicSongList>().lambda()
                .eq(MusicSongList::getSongListId, songList))
                .stream().map(MusicSongList::getMusicId).distinct().collect(Collectors.toList());
    }

    @Override
    public void addMusicToSongList(AddOrDeleteMusicToSongListReqVO reqVO) {
        if (reqVO.getMusicIds() == null || reqVO.getMusicIds().size() < 1) {
            return;
        }
        //查看已经存在的歌
        List<Long> existMusics = musicSongListDao.getExistSongs(reqVO.getSongListId());
        List<Long> musics = reqVO.getMusicIds();
        musics.removeAll(existMusics);

        List<MusicSongList> musicSongListList = musics.stream().map(l -> {
            MusicSongList musicSongList = new MusicSongList();
            musicSongList.setMusicId(l);
            musicSongList.setSongListId(reqVO.getSongListId());
            CommonUtils.fillWhenSave(musicSongList);
            return musicSongList;
        }).collect(Collectors.toList());
        musicSongListDao.saveBatch(musicSongListList);
    }

    @Override
    public void deleteMusicFromSongList(AddOrDeleteMusicToSongListReqVO reqVO) {
        //查看已经存在的歌
        List<Long> existMusics = musicSongListDao.getExistSongs(reqVO.getSongListId());
        reqVO.getMusicIds().removeAll(existMusics);
        musicSongListDao.removeByIds(reqVO.getMusicIds());
    }
}
