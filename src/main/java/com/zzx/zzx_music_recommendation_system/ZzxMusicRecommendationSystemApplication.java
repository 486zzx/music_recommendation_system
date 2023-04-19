package com.zzx.zzx_music_recommendation_system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.zzx.zzx_music_recommendation_system.mapper")
@EnableSwagger2
@EnableScheduling
@Slf4j
public class ZzxMusicRecommendationSystemApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ZzxMusicRecommendationSystemApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("报错原因 ============== ", e);
        }
    }


}
