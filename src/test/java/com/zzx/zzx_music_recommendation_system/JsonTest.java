package com.zzx.zzx_music_recommendation_system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzx.zzx_music_recommendation_system.utils.FileUtils;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.utils.SshSshdUtils;
import com.zzx.zzx_music_recommendation_system.vo.SshLink;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.pubkey.PublickeyAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.impl.DefaultSftpClientFactory;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Stream;

@SpringBootTest()
public class JsonTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${remote.server.ip}")
    private String ip;

    @Value("${remote.server.root}")
    private String root;

    @Value("${remote.server.password}")
    private String password;

    @Value("${remote.server.port}")
    private String port;

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
        SshSshdUtils sshSshdUtils = new SshSshdUtils(ip, root, Integer.parseInt(port), password);
        Path path = Paths.get("","src","main","resources", "mp3");
        List<String> list = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.forEach(p -> list.add(String.valueOf(p.getFileName())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s : list) {
            sshSshdUtils.sftpPutFile("D:\\learn\\JavaProject\\zzx_music_recommendation_system\\src\\main\\resources\\mp3\\背包-苏有朋.320.mp3",
                    "/mydirectory/music/背包-苏有朋.320.mp3");
        }

//        SshSshdUtils sshSshdUtils = new SshSshdUtils(ip, root, Integer.parseInt(port), password);
//
//
//        StopWatch stopWatch=new StopWatch();
//        SshLink sshLink=JSON.parseObject(sysServicePO.getServerAddress(), SshLink.class);
//        try {
//            stopWatch.start();
//            SshSshdUtils sshSshdUtils = new SshSshdUtils(sshLink.getHost(), sshLink.getUserName(), sshLink.getPort(), sshLink.getPassword());
//            sshSshdUtils.initialSession();
//            try {
//                log.info("开始执行ant dump、ant report命令:{}","cd "+sshLink.getCommandExecutionDirectory()+StringConstants.SEMICOLON_STRING+"ant dump"+StringConstants.SEMICOLON_STRING+"ant report");
//                sshSshdUtils.runCommand("source /etc/profile && source ~/.bash_profile && "+"cd "+sshLink.getCommandExecutionDirectory()+StringConstants.SEMICOLON_STRING+"ant dump"+StringConstants.SEMICOLON_STRING+"ant report");
//            } catch (Exception e) {
//                log.error("ant dump、ant report失败:{}",e.getMessage());
//                throw new BusinessException(e.getMessage());
//            }
//            try {
//                log.info("开始获取jacoco目录:{}",sshLink.getJacocoRootDirectory());
//                sshSshdUtils.scpGetFile(systemConfig.getJacocoReportLocation() + sysServicePO.getEid(), sshLink.getJacocoRootDirectory());
//            } catch (Exception e) {
//                log.error("获取jacoco目录失败:{}",e.getMessage());
//                throw new BusinessException(e.getMessage());
//            }
//            try {
//                log.info("关闭jacoco启动文件：{}",sshLink.getProjectRootDirectory()+"appjacoco.sh stop");
//                sshSshdUtils.runCommand("cd "+sshLink.getProjectRootDirectory()+StringConstants.SEMICOLON_STRING+"./appjacoco.sh stop");
//                log.info("开启原始启动文件：{}",sshLink.getProjectRootDirectory()+"app.sh start");
//                sshSshdUtils.runCommand("cd "+sshLink.getProjectRootDirectory()+StringConstants.SEMICOLON_STRING+"./app.sh start");
//            } catch (Exception e) {
//                log.error("./aap.sh stop、./appjacoco.sh start:{}",e.getMessage());
//                throw new BusinessException(e.getMessage());
//            }
//            try {
//                sshSshdUtils.close();
//            } catch (Exception e) {
//                log.error("ssh连接关闭错误", e);
//                throw new BusinessException(400, "ssh连接关闭失败！");
//            }
//        } catch (Exception e) {
//            throw new BusinessException(e.getMessage());
//        } finally {
//            stopWatch.stop();
//        }
//        ListOperations<String,User> listOps = redisTemplate.opsForList();
//        User user = new User("李四",18,new Date());
//        ArrayList<User> userList = new ArrayList<>();
//        userList.add(user);
//        userList.add(user);
//        listOps.leftPushAll("users",userList);
//        List<User> lists = listOps.range("users", 0, -1);
//        lists.forEach(x-> System.out.println(x.getUserName()+"=="+x.getBir()));
    }

    public static void main(String[] args) throws IOException {

            HashMap<Integer, String> hashMap = new HashMap<>();
            hashMap.put(1, "apple");
            hashMap.put(2, "banana");
            hashMap.put(3, "orange");

            // 获取所有值的集合
            Collection<String> values = hashMap.values();

            // 遍历集合
            for (String value : values) {
                System.out.println(value);
            }



        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setHost("47.109.76.26");
        sshd.setPort(22);

        // Set up authentication
        sshd.setPublickeyAuthenticator(new PublickeyAuthenticator() {
            @Override
            public boolean authenticate(String username, PublicKey key, ServerSession session) {
                // Add your public key authentication logic here
                return true;
            }
        });

        // Set up host key
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(Paths.get("hostkey.ser")));

        // Set up SFTP subsystem
        sshd.setSubsystemFactories(Collections.singletonList(new SftpSubsystemFactory()));

        // Set up virtual file system
        Path rootDir = Paths.get("/path/to/remote/root");
        Files.createDirectories(rootDir);
        sshd.setFileSystemFactory(new VirtualFileSystemFactory(rootDir));

        // Start SSH server
        sshd.start();

        // Transfer file
        String localFilePath = "D:\\learn\\JavaProject\\zzx_music_recommendation_system\\src\\main\\resources\\mp3\\背包-苏有朋.320.mp3";
        String remoteFilePath = "/root/mydirectory/music/背包-苏有朋.320.mp3";
        try (SshClient sshClient = SshClient.setUpDefaultClient()) {
            sshClient.start();
            try (ClientSession session = sshClient.connect("root" ,"47.109.76.26", 22).verify().getSession()) {
                session.addPasswordIdentity("djg.d9kjh.6fhg4fh");
                session.auth().verify();

                try (SftpClient sftpClient = new DefaultSftpClientFactory().createSftpClient(session)) {
//                    sftpClient.put(localFilePath, remoteFilePath);
                }
            }
        }

        // Stop SSH server
        sshd.stop();
    }


//    public static void main(String[] args) throws IOException {
//
//
//
////        String s = "gasga" + null + "adgfa";
////        System.out.println(s);
//
//
////        ClassLoader classLoader = JsonTest.class.getClassLoader();
////        String path = classLoader.getResource("test.json").getPath();
////        String s = readJsonFile(path);
////        String[] ss = s.split("\\n");
////        System.out.println(ss);
//
//
////        JSONObject jobj = JSON.parseObject(s);
////        JSONArray movies = jobj.getJSONArray("RECORDS");//构建JSONArray数组
////        for (int i = 0 ; i < movies.size();i++){
////            JSONObject key = (JSONObject)movies.get(i);
////            String name = (String)key.get("name");
////            String director = (String)key.get("director");
////            String scenarist=((String)key.get("scenarist"));
////            String actors=((String)key.get("actors"));
////            String type=((String)key.get("type"));
////            String ratingNum=((String)key.get("ratingNum"));
////            String tags=((String)key.get("tags"));
////            System.out.println(name);
////            System.out.println(director);
////            System.out.println(scenarist);
////            System.out.println(actors);
////            System.out.println(type);
////            System.out.println(director);
////            System.out.println(ratingNum);
////            System.out.println(tags);
////        }
//    }
}