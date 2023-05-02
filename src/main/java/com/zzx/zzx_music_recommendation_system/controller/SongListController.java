package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.service.SongListService;
import com.zzx.zzx_music_recommendation_system.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/zzx_music_recommendation_system/song-list")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @ApiOperation("新建/修改歌单")
    @PostMapping("/addOrUpdateSongList")
    public ResVO<Void> addOrUpdateSongList(@RequestBody @Valid ReqVO<AddOrUpdateSongListReqVO> reqVO) {
        try {
            songListService.addOrUpdateSongList(reqVO.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("查看我的歌单列表")
    @PostMapping("/getAllSongList")
    public ResVO<List<SongList>> getAllSongList() {
        try {
            return ResVO.ok(songListService.getAllSongList());
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("删除歌单")
    @PostMapping("/deleteSongList")
    public ResVO<Void> deleteSongList(@RequestBody @Valid ReqVO<Long> reqVO) {
        try {
            songListService.deleteSongList(reqVO.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("歌单详情")
    @PostMapping("/getSongListDetail")
    public ResVO<GetSongListDetailResVO> getSongListDetail(@RequestBody @Valid ReqVO<Long> reqVO) {
        try {
            return ResVO.ok(songListService.getSongListDetail(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("获取歌单内歌曲(分页)")
    @PostMapping(value = "/getMusicsFromSongList")
    public ResVO<PageVO<RankResVO>> getMusicsFromSongList(@RequestBody @Valid ReqVO<GetMusicsFromSongListReqVO> reqVO) {
        try {
            return ResVO.ok(songListService.getMusicsFromSongList(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("获取歌单内歌曲")
    @PostMapping(value = "/getAllMusicFromSongList")
    public ResVO<List<RankResVO>> getAllMusicFromSongList(@RequestBody @Valid ReqVO<Long> reqVO) {
        try {
            return ResVO.ok(songListService.getAllMusicFromSongList(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("收藏歌单")
    @PostMapping("/collectSongList")
    public ResVO<Void> collectSongList(@RequestBody @Valid ReqVO<Long> reqVO) {
        try {
            songListService.collectSongList(reqVO.getArgs());
            ResVO<Void> resVO = ResVO.ok();
            resVO.setMessage("收藏成功!");
            return resVO;
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

}
