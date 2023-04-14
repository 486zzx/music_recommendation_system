package com.zzx.zzx_music_recommendation_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.zzx.zzx_music_recommendation_system.mapper")
@EnableSwagger2
public class ZzxMusicRecommendationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzxMusicRecommendationSystemApplication.class, args);
    }


}
