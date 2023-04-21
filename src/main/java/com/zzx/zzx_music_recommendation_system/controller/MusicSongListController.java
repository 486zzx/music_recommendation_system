package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.service.MusicSongListService;
import com.zzx.zzx_music_recommendation_system.vo.GetOrSetCommentReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/zzx_music_recommendation_system/music-song-list")
public class MusicSongListController {

    @Autowired
    private MusicSongListService musicSongListService;

    @ApiOperation("将音乐添加到当前播放列表")
    @PostMapping("/addMusicToPlayList")
    public ResVO<Void> addMusicToPlayList(@RequestBody @Valid ReqVO<List<Long>> reqVO) {
        try {
            musicSongListService.addMusicToPlayList(reqVO.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("将音乐从当前播放列表中移除")
    @PostMapping("/deleteMusicFromPlayList")
    public ResVO<Void> deleteMusicFromPlayList(@RequestBody @Valid ReqVO<List<Long>> reqVO) {
        try {
            musicSongListService.deleteMusicFromPlayList(reqVO.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }


}
