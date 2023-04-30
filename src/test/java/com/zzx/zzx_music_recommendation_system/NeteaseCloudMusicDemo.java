package com.zzx.zzx_music_recommendation_system;

import com.alibaba.fastjson.JSONObject;
import top.yumbo.util.music.musicImpl.netease.NeteaseCloudMusicInfo;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/28 13:17
 */
public class NeteaseCloudMusicDemo {
    public static void main(String[] args) {
        final NeteaseCloudMusicInfo neteaseCloudMusicInfo = new NeteaseCloudMusicInfo();// 得到封装网易云音乐信息的工具类
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","1");
        final JSONObject login = neteaseCloudMusicInfo.banner(jsonObject);
        System.out.println(login);
    }
}
