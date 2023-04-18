package com.zzx.zzx_music_recommendation_system.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;

public interface UserInfoDao extends IService<UserInfo>  {

    public boolean isEmailExist(String email);

    public UserInfo isUserExist(String userEmail, String userPassword);

    public void login(String email);

}
