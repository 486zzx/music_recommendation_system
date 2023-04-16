package com.zzx.zzx_music_recommendation_system.utils;

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
            gmtCreated.setAccessible(true);
            gmtCreated.set(t, LocalDateTime.now());
            fillWhenUpdate(t);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static <T> void fillWhenUpdate(T t) {
        try {
            Field gmtModified = t.getClass().getDeclaredField("gmtModified");
            gmtModified.setAccessible(true);
            gmtModified.set(t, LocalDateTime.now());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
