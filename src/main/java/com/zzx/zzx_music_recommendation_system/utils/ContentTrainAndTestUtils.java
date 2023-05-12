/*
package com.zzx.zzx_music_recommendation_system.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

*/
/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/5/8 21:26
 *//*

public class ContentTrainAndTestUtils {

    public static void init(Map<String, Long> musicMap) {
        File folder = new File("src/main/resources/lastfm_subset"); // 将路径替换为要读取的文件夹的路径
        List<JSONObject> jsonObjects = new ArrayList<JSONObject>();

        // 处理解析后的JSONObject对象列表
    }

    public static Map<Long, Set<String>> readJSONFiles(String folderPath, Map<String, Long> musicMap) {
        List<String> jsonContents = new ArrayList<>();

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String jsonContent = new String(bytes, StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSONObject.parseObject(jsonContent);
                    //
                    String musicName = jsonObject.getString("title");
                    JSONArray tags = jsonObject.getJSONArray("tags");
                    for (Object tag : tags) {
                        JSONArray tagJson = (JSONArray) tag;
                    }

                    jsonContents.add(jsonContent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                List<String> subFolderJSONContents = readJSONFiles(file.getPath(), musicMap);
                jsonContents.addAll(subFolderJSONContents);
            }
        }

        return jsonContents;
    }
}
*/
