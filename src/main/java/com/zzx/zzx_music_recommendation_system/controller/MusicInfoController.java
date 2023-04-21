package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.zzx.zzx_music_recommendation_system.service.SingerInfoService;
import com.zzx.zzx_music_recommendation_system.vo.MusicDetailResVO;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;
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
@RequestMapping("/zzx_music_recommendation_system/music-info")
public class MusicInfoController {

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private SingerInfoService singerInfoService;

    @Autowired
    private LikeInfoService likeInfoService;

    @ApiOperation("获取歌曲信息")
    @PostMapping(value = "/getRank")
    public ResVO<List<RankResVO>> getRank(@RequestBody @Valid ReqVO<List<Long>> reqVO) {
        return ResVO.ok(musicInfoService.getRank(reqVO.getArgs()));
    }


    @ApiOperation("歌曲详情")
    @PostMapping(value = "/musicDetail")
    public ResVO<MusicDetailResVO> musicDetail(@RequestBody @Valid ReqVO<Long> reqVO) {
        try {
            return ResVO.ok(musicInfoService.musicDetail(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }



}
