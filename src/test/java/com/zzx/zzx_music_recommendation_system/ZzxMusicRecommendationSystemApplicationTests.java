package com.zzx.zzx_music_recommendation_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ZzxMusicRecommendationSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        System.out.println(s);

    }

}
