package com.zzx.zzx_music_recommendation_system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.*;
import java.util.List;

@SpringBootTest()
public class JsonTest {

    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    void testRedisTemplate(){
//        ListOperations<String,User> listOps = redisTemplate.opsForList();
//        User user = new User("李四",18,new Date());
//        ArrayList<User> userList = new ArrayList<>();
//        userList.add(user);
//        userList.add(user);
//        listOps.leftPushAll("users",userList);
//        List<User> lists = listOps.range("users", 0, -1);
//        lists.forEach(x-> System.out.println(x.getUserName()+"=="+x.getBir()));
    }


    public static void main(String[] args) {


        String s = "gasga" + null + "adgfa";
        System.out.println(s);


//        ClassLoader classLoader = JsonTest.class.getClassLoader();
//        String path = classLoader.getResource("test.json").getPath();
//        String s = readJsonFile(path);
//        String[] ss = s.split("\\n");
//        System.out.println(ss);


//        JSONObject jobj = JSON.parseObject(s);
//        JSONArray movies = jobj.getJSONArray("RECORDS");//构建JSONArray数组
//        for (int i = 0 ; i < movies.size();i++){
//            JSONObject key = (JSONObject)movies.get(i);
//            String name = (String)key.get("name");
//            String director = (String)key.get("director");
//            String scenarist=((String)key.get("scenarist"));
//            String actors=((String)key.get("actors"));
//            String type=((String)key.get("type"));
//            String ratingNum=((String)key.get("ratingNum"));
//            String tags=((String)key.get("tags"));
//            System.out.println(name);
//            System.out.println(director);
//            System.out.println(scenarist);
//            System.out.println(actors);
//            System.out.println(type);
//            System.out.println(director);
//            System.out.println(ratingNum);
//            System.out.println(tags);
//        }
    }
}