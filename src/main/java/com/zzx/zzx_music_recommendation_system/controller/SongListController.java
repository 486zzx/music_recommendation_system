package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.service.SongListService;
import com.zzx.zzx_music_recommendation_system.vo.AddOrUpdateSongListReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
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


}
