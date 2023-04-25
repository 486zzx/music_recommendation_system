package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.service.MusicTypeService;
import com.zzx.zzx_music_recommendation_system.service.UserTypeService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/zzx_music_recommendation_system/music-type")
public class MusicTypeController {

    @Autowired
    private MusicTypeService musicTypeService;

    @ApiOperation("获得所有音乐标签")
    @PostMapping(value = "/getMusicType")
    public ResVO<List<MusicType>> getMusicType() {
        try {
            musicTypeService.getMusicType();
            return ResVO.ok();
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

}
