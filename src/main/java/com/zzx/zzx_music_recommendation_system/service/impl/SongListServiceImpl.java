package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.zzx_music_recommendation_system.constant.StringConstants;
import com.zzx.zzx_music_recommendation_system.dao.*;
import com.zzx.zzx_music_recommendation_system.entity.*;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.SongListMapper;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.zzx.zzx_music_recommendation_system.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.utils.PageUtils;
import com.zzx.zzx_music_recommendation_system.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    @Autowired
    private SongListDao songListDao;

    @Autowired
    private MusicSongListDao musicSongListDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private UserSongListDao userSongListDao;

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
        List<SongList> songLists = list(new QueryWrapper<SongList>().lambda()
                .eq(SongList::getSongListType, SongListTypeEnum.COMMON_SONG_LIST.getCode())
                .eq(SongList::getUserId, UserInfoUtil.getUserId()));
        return songLists;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void deleteSongList(Long songListId) {
        songListDao.removeById(songListId);
        musicSongListDao.remove(new QueryWrapper<MusicSongList>().lambda()
                .eq(MusicSongList::getSongListId, songListId));
    }

    @Override
    public GetSongListDetailResVO getSongListDetail(Long songListId) {
        //歌单信息
        GetSongListDetailResVO resVO = new GetSongListDetailResVO();
        SongList songList = songListDao.getById(songListId);
        BeanUtil.copyProperties(songList, resVO);
        //收藏数获取
        List<UserSongList> list = userSongListDao.list(new QueryWrapper<UserSongList>().lambda()
                .eq(UserSongList::getSongListId, songListId));
        resVO.setCollectNum((long) (list != null ? list.size() : 0));
        //用户名、邮箱获取
        if (Objects.nonNull(resVO.getUserId())) {
            UserInfo userInfo = userInfoDao.getById(resVO.getUserId());
            if (userInfo != null) {
                resVO.setEmail(userInfo.getUserEmail());
                resVO.setUserName(userInfo.getName());
            }
        }
        return resVO;
    }

    @Override
    public PageVO<RankResVO> getMusicsFromSongList(GetMusicsFromSongListReqVO songListId) {
        List<Long> list = musicSongListDao.getExistSongs(songListId.getSongListId());
        return musicInfoService.getMusicsPage(new Page<>(songListId.getCurrent(), songListId.getSize()), list);
    }

    @Override
    public void collectSongList(Long songListId) {
        UserSongList userSongList = userSongListDao.getOne(new QueryWrapper<UserSongList>().lambda()
                .eq(UserSongList::getSongListId, songListId)
                .eq(UserSongList::getUserId, UserInfoUtil.getUserId())
                .last(StringConstants.LIMIT_1));
        if (userSongList != null) {
            throw new MyException("已经收藏该歌单");
        }
        userSongList = new UserSongList();
        CommonUtils.fillWhenSave(userSongList);
        userSongList.setSongListId(songListId);
        userSongList.setUserId(UserInfoUtil.getUserId());
        userSongListDao.save(userSongList);
    }

    @Override
    public List<RankResVO> getAllMusicFromSongList(Long songListId) {
        List<Long> list = musicSongListDao.getExistSongs(songListId);
        return musicInfoService.getMusics(list);
    }

}
