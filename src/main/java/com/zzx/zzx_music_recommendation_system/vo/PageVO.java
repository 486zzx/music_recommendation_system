package com.zzx.zzx_music_recommendation_system.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/25 16:46
 */
@Data
@NoArgsConstructor
public class PageVO<T> implements Serializable {

    private long total;
    private long size;
    private long current;
    private long pages;
    private List<T> records;

    public long getPages() {
        if (pages > 0) {
            return pages;
        }
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    public static <T> PageVO<T> emptyPage(Long current, Long size) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setCurrent(current);
        pageVO.setSize(size);
        pageVO.setTotal(0);
        pageVO.setRecords(new ArrayList<>());
        return pageVO;
    }
}
