package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.service.UserSongListService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.GetHotSongListResVO;
import com.zzx.zzx_music_recommendation_system.vo.LoginReqVO;
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
@RequestMapping("/zzx_music_recommendation_system/user-song-list")
public class UserSongListController {

    @Autowired
    private UserSongListService userSongListService;

    @ApiOperation("获取热门歌单")
    @PostMapping(value = "/getHotSongList")
    public ResVO<List<GetHotSongListResVO>> getHotSongList() {
        try {
            return ResVO.ok(userSongListService.getHotSongList());
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

}
