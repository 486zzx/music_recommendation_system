package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.UserTypeDao;
import com.zzx.zzx_music_recommendation_system.entity.UserType;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.UserTypeMapper;
import com.zzx.zzx_music_recommendation_system.service.UserTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-04-03
 */
@Service
public class UserTypeServiceImpl extends ServiceImpl<UserTypeMapper, UserType> implements UserTypeService {

    @Autowired
    private UserTypeDao userTypeDao;

    @Override
    public void fillUserLikeType(List<Long> musicTypeIds) {
        List<UserType> userTypeList = new ArrayList<>();
        for (Long musicTypeId : musicTypeIds) {
            UserType userType = new UserType();
            userType.setUserId(UserInfoUtil.getUserId());
            userType.setTypeId(musicTypeId);
            CommonUtils.fillWhenSave(userType);
            userTypeList.add(userType);
        }
        userTypeDao.saveBatch(userTypeList);
    }

    @Override
    public boolean isExistUserLikeType() {
        List<UserType> list = userTypeDao.list(new QueryWrapper<UserType>().lambda()
                .eq(UserType::getUserId, UserInfoUtil.getUserId()));
        return list != null && list.size() >= 1;
    }
}
