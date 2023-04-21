package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.MusicSongListDao;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicSongList;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.SongListMapper;
import com.zzx.zzx_music_recommendation_system.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.vo.AddOrUpdateSongListReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListDao songListDao;

    @Autowired
    private MusicSongListDao musicSongListDao;

    @Override
    public void addOrUpdateSongList(AddOrUpdateSongListReqVO reqVO) {
        SongList songList = new SongList();
        BeanUtil.copyProperties(reqVO, songList);
        songList.setSongListType(SongListTypeEnum.COMMON_SONG_LIST.getCode());
        if (songList.getSongListId() == null) {
            CommonUtils.fillWhenSave(songList);
            songListDao.save(songList);
        } else {
            CommonUtils.fillWhenUpdate(songList);
            songListDao.updateById(songList);
        }
    }

    @Override
    public List<SongList> getAllSongList() {
        return list(new QueryWrapper<SongList>().lambda()
                .eq(SongList::getSongListType, SongListTypeEnum.COMMON_SONG_LIST.getCode())
                .eq(SongList::getUserId, UserInfoUtil.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void deleteSongList(Long songListId) {
        songListDao.removeById(songListId);
        musicSongListDao.remove(new QueryWrapper<MusicSongList>().lambda()
                .eq(MusicSongList::getSongListId, songListId));
    }

}
