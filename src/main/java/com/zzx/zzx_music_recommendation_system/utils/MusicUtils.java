package com.zzx.zzx_music_recommendation_system.utils;

import com.alibaba.fastjson.JSONObject;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import top.yumbo.util.music.musicImpl.netease.NeteaseCloudMusicInfo;

import java.io.File;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/19 20:46
 */
public class MusicUtils {

    static final NeteaseCloudMusicInfo neteaseCloudMusicInfo = new NeteaseCloudMusicInfo();
    private static final String s = "http://music.163.com/song/media/outer/url?id=";

    public static void login() {
        JSONObject j = new JSONObject();
        JSONObject key = neteaseCloudMusicInfo.loginQrKey(j);
        neteaseCloudMusicInfo.loginQrCreate(key);
        neteaseCloudMusicInfo.loginQrCreate(key);
    }

    public static void main(String[] args) {
        login();
        System.out.println("用户信息：" + neteaseCloudMusicInfo.userAccount());
        JSONObject j = new JSONObject();
        j.put("offset", 0);
        j.put("albumType", 1);
        JSONObject res = neteaseCloudMusicInfo.album_songsaleboard(j);
        System.out.println(res);
    }

}
