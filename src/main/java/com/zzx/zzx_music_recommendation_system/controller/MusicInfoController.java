package com.zzx.zzx_music_recommendation_system.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.zzx.zzx_music_recommendation_system.service.MusicInfoService;
import com.zzx.zzx_music_recommendation_system.service.SingerInfoService;
import com.zzx.zzx_music_recommendation_system.utils.DateUtils;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import com.zzx.zzx_music_recommendation_system.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yumbo.util.music.musicImpl.netease.NeteaseCloudMusicInfo;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
@RequestMapping("/zzx_music_recommendation_system/music-info")
public class MusicInfoController {

    @Autowired
    private MusicInfoService musicInfoService;

    @Autowired
    private SingerInfoService singerInfoService;

    @Autowired
    private LikeInfoService likeInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation("获取歌曲信息")
    @PostMapping(value = "/getMusics")
    public ResVO<List<RankResVO>> getMusics(@RequestBody @Valid ReqVO<List<Long>> reqVO) {
        try {
            return ResVO.ok(musicInfoService.getMusics(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("获取月榜")
    @PostMapping(value = "/getMouthHotMusics")
    public ResVO<List<String>> getMouthHotMusics() {
        LocalDateTime time = LocalDateTime.now().minusMonths(1);
        try {
            List<String> mouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), new TypeReference<List<String>>() {});
            return ResVO.ok(mouth);
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("获取日榜")
    @PostMapping(value = "/getDayHotMusics")
    public ResVO<List<String>> getDayHotMusics() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        try {
            List<String> mouth = redisUtils.getList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time), new TypeReference<List<String>>() {});
            return ResVO.ok(mouth);
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
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

    @ApiOperation("随机歌曲")
    @PostMapping(value = "/getRandomMusic")
    public ResVO<List<RankResVO>> getRandomMusic() {
        try {
            return ResVO.ok(musicInfoService.getRandomMusic());
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("获取评论")
    @PostMapping(value = "/getComments")
    public ResVO<PageVO<CommentVO>> getComments(@RequestBody @Valid ReqVO<GetCommentsReqVO> reqVO) {
        try {
            return ResVO.ok(musicInfoService.getComments(reqVO.getArgs()));
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("最新歌曲")
    @PostMapping(value = "/getLastMusic")
    public ResVO<List<RankResVO>> getLastMusic() {
        try {
            return ResVO.ok(musicInfoService.getLastMusic());
        } catch (Exception e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("网易云轮播图")
    @PostMapping(value = "/banner")
    public JSONObject banner() {
        final NeteaseCloudMusicInfo neteaseCloudMusicInfo = new NeteaseCloudMusicInfo();// 得到封装网易云音乐信息的工具类
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","0");
        final JSONObject login = neteaseCloudMusicInfo.banner(jsonObject);
        return login;
    }

}
