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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
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
    public ResVO<UserInfo> getUser(@RequestBody UserInfo userInfo) {
        userInfo.setValue1("haha");
        ResVO<UserInfo> resVO = new ResVO<>();
        resVO.setData(userInfo);
        return resVO;

    }



    @ApiOperation("发送验证码")
    @PostMapping(value = "/sendValidateCode")
    public ResVO<Void> sendValidateCode(@RequestBody ReqVO<String> reqVO, HttpServletRequest request) {
        try {
            userInfoService.sendValidateCode(reqVO.getArgs(), request);
            return ResVO.ok();
        } catch (MyException e) {
            return ResVO.fail(e.getMessage());
        }
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public ResVO<Void> register(@RequestBody ReqVO<RegisterReqVO> reqVO) {
        return ResVO.ok();
    }


}
