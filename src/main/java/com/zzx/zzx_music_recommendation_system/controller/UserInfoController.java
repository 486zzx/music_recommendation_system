package com.zzx.zzx_music_recommendation_system.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.service.RecommendService;
import com.zzx.zzx_music_recommendation_system.service.UserInfoService;
import com.zzx.zzx_music_recommendation_system.service.impl.MailService;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
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
@Api(tags = "用户管理")
@RestController
@RequestMapping("/zzx_music_recommendation_system/user-info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RecommendService recommendService;


    @ApiOperation("可以指定参数的API")
    @PostMapping(value = "/getUser")
    public ResVO<List<UserInfo>> getUser() {
        return ResVO.ok(userInfoService.list());

    }



    @ApiOperation("发送验证码")
    @PostMapping(value = "/sendValidateCode")
    public ResVO<Void> sendValidateCode(@RequestBody @Valid ReqVO<String> reqVO, HttpServletRequest request) {
        try {
            userInfoService.sendValidateCode(reqVO.getArgs(), request);
            return ResVO.ok();
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public ResVO<Void> register(@RequestBody @Valid ReqVO<RegisterReqVO> reqVO) {
        try {
            userInfoService.register(reqVO.getArgs());
            return ResVO.ok();
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public ResVO<String> login(@RequestBody @Valid ReqVO<LoginReqVO> reqVO) {
        try {
            return ResVO.ok(userInfoService.login(reqVO.getArgs()));
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }


}
