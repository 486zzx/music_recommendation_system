package com.zzx.zzx_music_recommendation_system.utils;

import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 10:14
 */
public class CommonUtils {

    public static <T> void fillWhenSave(T t) {
        try {
            Field gmtCreated = t.getClass().getDeclaredField("gmtCreated");
            Field createUserId = t.getClass().getDeclaredField("createUserId");
            gmtCreated.setAccessible(true);
            createUserId.setAccessible(true);
            gmtCreated.set(t, LocalDateTime.now());
            createUserId.set(t, UserInfoUtil.getUserInfo());
            fillWhenUpdate(t);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> void fillWhenUpdate(T t) {
        try {
            Field gmtModified = t.getClass().getDeclaredField("gmtModified");
            Field modifyUserId = t.getClass().getDeclaredField("modifyUserId");
            gmtModified.setAccessible(true);
            modifyUserId.setAccessible(true);
            gmtModified.set(t, LocalDateTime.now());
            modifyUserId.set(t, UserInfoUtil.getUserInfo());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
