package com.zzx.zzx_music_recommendation_system;

import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chen0040.tensorflow.classifiers.models.cifar10.Cifar10AudioClassifier;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.utils.FileUtils;
import com.zzx.zzx_music_recommendation_system.utils.MusicUtils;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import net.minidev.json.writer.JsonReader;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ZzxMusicRecommendationSystemApplicationTests {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private RedisUtils redisUtils;//二进制(能取，在可视化工具中看不到数据)

    @Autowired
    private StringRedisTemplate stringRedisTemplate;//RedisTemplate 的子类

    @Test
    void contextLoads() throws Exception {
        File file = new File("src/main/resources/mp3/喜欢你.mp3");
        AudioFile audioFile = AudioFileIO.read(file);
        Tag tag = audioFile.getTag();
        String artist = tag.getFirst(FieldKey.ARTIST);
        String title = tag.getFirst(FieldKey.TITLE);
        MusicUtils.getMusicInfo("src/main/resources/mp3/喜欢你.mp3");
    }


    @Test
    void testRedisTemplate(){
        UserInfo user = new UserInfo();
        user.setValue1("haha");
        ArrayList<UserInfo> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        redisUtils.setList(RedisUtils.Type.MOUTH_RANKING,"users", userList);
        List<UserInfo> lists =  redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, "users", new TypeReference<List<UserInfo>>() {});
        System.out.println(lists);
        System.out.println(lists.get(0).getValue1());
        System.out.println(redisUtils.deleteList(RedisUtils.Type.MOUTH_RANKING,"users"));
    }


    public static void main(String[] args) {
        String s = MailUtil.send("1960104079@qq.com", "ces", "content", false);
        String s1 = MailUtil.send("1960104adfg.com", "ces", "content", false);

        System.out.println(s);
        System.out.println(s1);
    }

    // 操作字符串类型
//    @Test
//    public void test01() throws Exception {
//        // 获取string操作对象
//        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//
//        // 存值
//        opsForValue.set("city", "nan");
//
//        // 取值
//        String value = opsForValue.get("city");
//        System.out.println(value);
//
//        // 存验证码存活5分钟
//        opsForValue.set("sms_13700137000", "6375", 60L,TimeUnit.SECONDS);
//
//        //删除
//        redisTemplate.delete("city");
//
//    }

    /*public static void main(String[] args) throws IOException {
        Cifar10AudioClassifier classifier = new Cifar10AudioClassifier();
        classifier.load_model();

        // List<String> paths = FileUtils.getAudioFiles();
        List<String> paths= FileUtils.getAudioFiles();

        Collections.shuffle(paths);

        for (String path : paths) {
            System.out.println("Predicting " + path + " ...");
            File f = new File(path);
            String label = classifier.predict_audio(f);

            System.out.println("Predicted: " + label);
        }
    }*/

}
