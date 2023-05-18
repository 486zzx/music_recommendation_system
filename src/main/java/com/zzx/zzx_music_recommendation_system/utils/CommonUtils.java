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
            fillWhenSaveNoLogin(t);
            Field createUserId = t.getClass().getDeclaredField("createUserId");
            createUserId.setAccessible(true);
            createUserId.set(t, UserInfoUtil.getUserId());
            Field modifyUserId = t.getClass().getDeclaredField("modifyUserId");
            modifyUserId.setAccessible(true);
            modifyUserId.set(t, UserInfoUtil.getUserId());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> void fillWhenSaveNoLogin(T t) {
        try {
            Field gmtCreated = t.getClass().getDeclaredField("gmtCreated");
            gmtCreated.setAccessible(true);
            gmtCreated.set(t, LocalDateTime.now());
            fillWhenUpdateNoLogin(t);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> void fillWhenUpdate(T t) {
        try {
            fillWhenUpdateNoLogin(t);
            Field modifyUserId = t.getClass().getDeclaredField("modifyUserId");
            modifyUserId.setAccessible(true);
            modifyUserId.set(t, UserInfoUtil.getUserId());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> void fillWhenUpdateNoLogin(T t) {
        try {
            Field gmtModified = t.getClass().getDeclaredField("gmtModified");
            Field isDelete = t.getClass().getDeclaredField("isDelete");
            gmtModified.setAccessible(true);
            isDelete.setAccessible(true);
            gmtModified.set(t, LocalDateTime.now());
            isDelete.set(t, 1);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static int compare(Float f1, Float f2) {
        if (f1 > f2) {
            return 1;
        } else if (f2 > f1) {
            return -1;
        } else {
            return 0;
        }
    }


}
