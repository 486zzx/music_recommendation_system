package com.zzx.zzx_music_recommendation_system.utils;

import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Frame;

import java.io.File;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/19 20:46
 */
public class MusicUtils {

    private static final String SONG_NAME_KEY = "TIT2";
    private static final String ARTIST_KEY = "TPE1";

    /**
     * 通过歌曲文件地址, 获取歌曲信息
     *
     * @param filePath 歌曲文件地址
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    public static MusicInfo getMusicInfo(String filePath) throws Exception {

        MusicInfo music = null;

        try {

            MP3File mp3File = (MP3File) AudioFileIO.read(new File(filePath));

            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

            // 歌曲名称
            String songName = getInfoFromFrameMap(mp3File, SONG_NAME_KEY);
            // 歌手名称
            String artist = getInfoFromFrameMap(mp3File, ARTIST_KEY);
            // 播放时长
            int duration = audioHeader.getTrackLength();

            // 封装到music对象
            music = new MusicInfo();
            music.setMusicName(songName);
            music.setSingerId(artist);
            music.setListenTimes((long) duration);
            music.setMusicContent(filePath);
            System.out.println(music);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取失败！" + e);
        }

        return music;
    }

    /**
     * 通过键值,获取歌曲中对应的字段信息
     *
     * @param mp3File mp3音乐文件
     * @param key     键值
     * @return 歌曲信息
     * @throws Exception 可能抛出空指针异常
     */
    private static String getInfoFromFrameMap(MP3File mp3File, String key) throws Exception {
        String artist = mp3File.getID3v2Tag().getFirst(FieldKey.ARTIST);
        String title = mp3File.getID3v2Tag().getFirst(FieldKey.TITLE);
        System.out.println(artist);
        System.out.println(mp3File.getID3v2Tag().getFirst(FieldKey.ALBUM));
        System.out.println(title);
        System.out.println(mp3File.getMP3AudioHeader().getTrackLength());

        ID3v23Frame frame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get(key);
        return frame.getContent();
    }
}
