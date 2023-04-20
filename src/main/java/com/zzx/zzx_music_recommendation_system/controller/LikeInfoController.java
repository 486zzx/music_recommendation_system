package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/zzx_music_recommendation_system/like-info")
public class LikeInfoController {

    @Autowired
    private LikeInfoService likeInfoService;

    @ApiOperation("播放音乐")
    @PostMapping("/playMusic")
    public ResVO<MusicInfo> playMusic(@RequestBody ReqVO<Long> musicId) {
        try {
            return ResVO.ok(likeInfoService.saveLikeInfo(musicId.getArgs(), SongListTypeEnum.PLAY));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("下载音乐")
    @PostMapping("/downloadMusic")
    public ResVO<MusicInfo> downloadMusic(@RequestBody ReqVO<Long> musicId, HttpServletResponse response) {
        try {
            return ResVO.ok(likeInfoService.saveDownloadLikeInfo(musicId.getArgs(), response));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("收藏音乐")
    @PostMapping("/collectMusic")
    public ResVO<Void> collectMusic(@RequestBody ReqVO<Long> musicId) {
        try {
            likeInfoService.saveLikeInfo(musicId.getArgs(), SongListTypeEnum.LIKE);
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("取消收藏")
    @PostMapping("/stopCollect")
    public ResVO<Void> stopCollect(@RequestBody ReqVO<Long> musicId) {
        try {
            likeInfoService.deleteLikeInfo(musicId.getArgs());
            return ResVO.ok();
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }



}
