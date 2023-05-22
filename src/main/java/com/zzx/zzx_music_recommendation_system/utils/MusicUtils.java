package com.zzx.zzx_music_recommendation_system.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.chen0040.tensorflow.classifiers.models.cifar10.Cifar10AudioClassifier;
import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.SingerInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.entity.SingerInfo;
import com.zzx.zzx_music_recommendation_system.enums.LabelEnum;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.ID3v23Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.yumbo.util.music.MusicEnum;
import top.yumbo.util.music.annotation.MusicService;
import top.yumbo.util.music.annotation.YumboAnnotationUtils;
import top.yumbo.util.music.musicAbstract.AbstractMusic;
import top.yumbo.util.music.musicImpl.netease.NeteaseCloudMusicInfo;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/19 20:46
 */
@Component
public class MusicUtils extends AbstractMusic {

    @Value("${remote.server.ip}")
    private String ip;

    @Value("${remote.server.root}")
    private String root;

    @Value("${remote.server.password}")
    private String password;

    @Value("${remote.server.port}")
    private String port;

    @Autowired
    private SingerInfoDao singerInfoDao;

    @Autowired
    private MusicInfoDao musicInfoDao;

    static final NeteaseCloudMusicInfo neteaseCloudMusicInfo = new NeteaseCloudMusicInfo();
    private static final String DOWNLOAD = "http://music.163.com/song/media/outer/url?id=";
    /**
     * url
     */
    private static final String URL = "https://autumnfish.cn/";
    /**
     * 获取热播榜
     */
    private static final String PLAYLIST_DETAIL = "https://autumnfish.cn/";

    private static final String MP3 = ".mp3";

    private static final String TEMP_PATH = "src/main/resources/mp3/";

    {
        setMusicEnum(MusicEnum.OtherMusic);
    }

    @Override
    public JSONObject getResult() {
        YumboAnnotationUtils.sendRequestAutowiredJson(this);
        return super.getResult();
    }

    /**
     * 有参数的接口，传入的是json，返回的也是json
     */
    @MusicService(url = "playlist/detail", serverAddress = "https://autumnfish.cn/")
    public JSONObject playListDetail(JSONObject parameter) {
        setCurrentRunningMethod("playListDetail");
        setParameter(parameter);// 将参数保存
        return getResult();
    }

    public static void main(String[] args) {
        String fileUrl = "http://music.163.com/song/media/outer/url?id=2046990697.mp3"; // 替换为您的文件URL
        String savePath = "src/main/resources/mp3/1.mp3"; // 替换为您希望保存文件的路径

        try {
            downloadFile(fileUrl, savePath);
            System.out.println("文件下载成功！");
        } catch (IOException e) {
            System.out.println("文件下载失败: " + e.getMessage());
        }
    }

    public static void downloadFile(String fileUrl, String savePath) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();

        try (InputStream in = new BufferedInputStream(connection.getInputStream());
             FileOutputStream out = new FileOutputStream(savePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }


    public static void login() {
        JSONObject j = new JSONObject();
        JSONObject key = neteaseCloudMusicInfo.loginQrKey(j);
        neteaseCloudMusicInfo.loginQrCreate(key);
        neteaseCloudMusicInfo.loginQrCreate(key);
    }

    //除非加音乐，否则注释掉@PostConstruct
//    @PostConstruct
    @Transactional(propagation = Propagation.NESTED)
    public void getMusic() throws IOException {
        Cifar10AudioClassifier classifier = new Cifar10AudioClassifier();
        classifier.load_model();
        JSONObject j = new JSONObject();
        j.put("id", "3778678");
        MusicUtils musicUtils = new MusicUtils();
        JSONObject res = musicUtils.playListDetail(j);
        JSONArray tracks = res.getJSONObject("playlist").getJSONArray("tracks");
        List<SingerInfo> singers = singerInfoDao.list();
        Set<Long> singerInfoSet = singers.stream().map(SingerInfo::getSingerId).collect(Collectors.toSet());
        Set<Long> musicInfoSet = musicInfoDao.list().stream().map(MusicInfo::getMusicId).collect(Collectors.toSet());
        List<MusicInfo> musicInfoList = new ArrayList<>();
        for (int i = 0; i < tracks.size(); i++) {
            JSONObject jo = tracks.getJSONObject(i);
            JSONArray ar = jo.getJSONArray("ar");
            Long musicId = jo.getLong("id");
            if (musicInfoSet.contains(musicId)) {
                continue;
            }
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setMusicId(musicId);
            musicInfo.setMusicName(jo.getString("name"));
            musicInfo.setValue1(jo.getJSONObject("al").getString("picUrl"));
            StringBuilder stringBuilder = new StringBuilder();
            //歌手
            List<SingerInfo> singerInfoList = new ArrayList<>();
            for (int k = 0; k < ar.size(); k++) {
                JSONObject tempArist = ar.getJSONObject(k);
                if (singerInfoSet.contains(tempArist.getLong("id"))) {
                    continue;
                }
                SingerInfo singerInfo = new SingerInfo();
                singerInfo.setSingerId(tempArist.getLong("id"));
                singerInfo.setSingerName(tempArist.getString("name"));
                singerInfo.setIsDelete(1);
                singerInfoList.add(singerInfo);
                if (stringBuilder.length() > 0) {
                    stringBuilder.append('|');
                }
                stringBuilder.append(singerInfo.getSingerId());
                singerInfoSet.add(singerInfo.getSingerId());
            }
            if (singerInfoList.size() != 0) {
                singerInfoDao.saveBatch(singerInfoList);
                musicInfo.setSingerId(stringBuilder.toString());
            }
            String url = DOWNLOAD + musicId + MP3;
            musicInfo.setMusicContent(url);
            try {
                downloadFile(url, TEMP_PATH + musicId + MP3);
                String typeId = String.valueOf(getLabelCode(TEMP_PATH + musicId + MP3, classifier));
                if (!"-1".equals(typeId)) {
                    musicInfo.setTypeIds(typeId);
                }
                deleteFile(TEMP_PATH + musicId + MP3);
            } catch (IOException e) {
                deleteFile(TEMP_PATH + musicId + MP3);
                continue;
            }
            musicInfo.setIsDelete(1);
            musicInfoList.add(musicInfo);
            musicInfoSet.add(musicInfo.getMusicId());
        }
        musicInfoDao.saveBatch(musicInfoList);
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("文件已删除");
            } else {
                System.out.println("文件删除失败");
            }
        } else {
            System.out.println("文件不存在");
        }
    }

    private static Integer getLabelCode(String path, Cifar10AudioClassifier classifier) throws IOException {
        File f = new File(path);
        String label = classifier.predict_audio(f);
        return LabelEnum.getCodeByLabel(label);
    }

}
