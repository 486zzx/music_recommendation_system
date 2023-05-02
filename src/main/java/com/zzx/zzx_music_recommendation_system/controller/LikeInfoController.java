package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/zzx_music_recommendation_system/like-info")
public class LikeInfoController {

    @Autowired
    private LikeInfoService likeInfoService;

    @ApiOperation("播放音乐")
    @PostMapping("/playMusic")
    public ResVO<MusicInfo> playMusic(@RequestBody @Valid ReqVO<Long> musicId) {
        try {
            return ResVO.ok(likeInfoService.saveLikeInfo(musicId.getArgs(), SongListTypeEnum.PLAY));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("批量播放音乐")
    @PostMapping("/playMusics")
    public ResVO<List<MusicInfo>> playMusics(@RequestBody @Valid ReqVO<List<Long>> musicId) {
        try {
            return ResVO.ok(likeInfoService.saveLikeInfoList(musicId.getArgs(), SongListTypeEnum.PLAY));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("下载音乐")
    @PostMapping("/downloadMusic")
    public ResVO<Void> downloadMusic(@RequestBody @Valid ReqVO<Long> musicId) {
        try {
            likeInfoService.saveLikeInfo(musicId.getArgs(), SongListTypeEnum.DOWNLOAD);
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("收藏音乐")
    @PostMapping("/collectMusic")
    public ResVO<Void> collectMusic(@RequestBody @Valid ReqVO<Long> musicId) {
        try {
            likeInfoService.saveLikeInfo(musicId.getArgs(), SongListTypeEnum.LIKE);
            ResVO<Void> resVO = ResVO.ok();
            resVO.setMessage("收藏成功！");
            return resVO;
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("取消收藏")
    @PostMapping("/stopCollect")
    public ResVO<Void> stopCollect(@RequestBody @Valid ReqVO<Long> musicId) {
        try {
            likeInfoService.deleteLikeInfo(musicId.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }



}
