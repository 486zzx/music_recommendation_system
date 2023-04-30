package com.zzx.zzx_music_recommendation_system.service;

import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.vo.LoginReqVO;
import com.zzx.zzx_music_recommendation_system.vo.RegisterReqVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 发送邮箱验证码
     * @param email
     */
    void sendValidateCode(String email);

    /**
     * 避免重复发送，设置发送间隔
     * @param request
     * @param minutes
     * @return
     */
    boolean tooQuickly(HttpServletRequest request, int minutes);


    void register(RegisterReqVO reqVO);

    String login(LoginReqVO reqVO);

}
