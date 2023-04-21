package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/zzx_music_recommendation_system/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

//    @ApiOperation("获得音乐标签")
//    @PostMapping(value = "/getMusicType")
//    public ResVO<List<MusicType>> getMusicType() {
//        try {
//            musicTypeService.getMusicType();
//            return ResVO.ok();
//        } catch (MyException e) {
//            return ResVO.fail(e.getMessage());
//        }
//    }

}
