package com.zzx.zzx_music_recommendation_system;

import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/21 15:23
 */
public class NewTest {

    public static void main(String[] args) {
        Map<Long, Long> map = new HashMap<>();
        map.put(10L, null);
        Long temp = map.get(null);
        System.out.println(temp);
    }
}
