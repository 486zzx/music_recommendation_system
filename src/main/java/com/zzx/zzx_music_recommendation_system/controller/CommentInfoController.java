package com.zzx.zzx_music_recommendation_system.controller;

import com.zzx.zzx_music_recommendation_system.entity.MusicType;
import com.zzx.zzx_music_recommendation_system.service.CommentInfoService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.ReqVO;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/zzx_music_recommendation_system/comment-info")
public class CommentInfoController {

    @Autowired
    private CommentInfoService commentInfoService;




}
