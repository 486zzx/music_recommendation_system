package com.zzx.zzx_music_recommendation_system.utils;


import cn.hutool.core.io.file.FileReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Evan
 * @Description:
 * @Date: Created in 9:33 2019/5/15
 * @Modified By:
 */


public class FileUtils {

    public static byte[] download(String str) {
        if (isLocalPath(str)) {
            return downloadUrl(str);
        }
        if (isUrl(str)) {
            return downloadLocal(str);
        }
        return null;
    }

    public static boolean isLocalPath(String path) {
        // 检查字符串是否以 "file://" 开头
        if (path.startsWith("file://")) {
            return true;
        }
        // 检查字符串是否以盘符开头（例如：C:\path\to\file）
        if (path.matches("^[A-Za-z]:\\\\.*")) {
            return true;
        }
        // 如果字符串不是以 "file://" 开头，也不是以盘符开头，则它不是本地路径
        return false;
    }

    public static boolean isUrl(String url) {
        String regex = "(http|https)://[\\S]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static byte[] downloadLocal(String musicContentUrl) {
        FileReader fileReader = new FileReader(musicContentUrl);
        return fileReader.readBytes();
    }

    public static byte[] downloadUrl(String musicContentUrl) {
        try {
            URL url = new URL(musicContentUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream()) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    return outputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    public static List<String> getAudioFiles() {
        List<String> result = new ArrayList<>();
        //File dir = new File("music_samples");
        File dir=new File("src/main/resources/mp3");
        System.out.println(dir.getAbsolutePath());
        if (dir.isDirectory()) {

            for (File f : dir.listFiles()) {
                String file_path = f.getAbsolutePath();
                if (file_path.toLowerCase().endsWith("mp3")) {
                    result.add(file_path);

                }
            }
        }

        return result;
    }

    public static void uploadFile(String serverUrl, String filePath) throws IOException {
        File file = new File(filePath);
        URL url = new URL(serverUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/octet-stream");
        connection.setRequestProperty("Content-Length", String.valueOf(file.length()));

        // 将文件写入连接
        OutputStream outputStream = connection.getOutputStream();
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();

        // 读取响应
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        String responseLine;
        while ((responseLine = reader.readLine()) != null) {
            System.out.println(responseLine);
        }
        reader.close();
        responseStream.close();
    }



}
