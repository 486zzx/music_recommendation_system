package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.entity.Recommend;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/zzx_music_recommendation_system/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @ApiOperation("获得用户的个性化推荐音乐")
    @PostMapping(value = "/getRecommendMusic")
    public ResVO<List<String>> getRecommendMusic() {
        try {
            return ResVO.ok(recommendService.getRecommendMusic());
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

}
