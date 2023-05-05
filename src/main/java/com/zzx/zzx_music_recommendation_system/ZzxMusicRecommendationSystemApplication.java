package com.zzx.zzx_music_recommendation_system;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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
