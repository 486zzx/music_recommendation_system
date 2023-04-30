package com.zzx.zzx_music_recommendation_system.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zzx.zzx_music_recommendation_system.vo.PageVO;

import java.util.List;
import java.util.function.Supplier;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/25 17:21
 */
public class PageUtils {
    private PageUtils(){
        throw new IllegalStateException();
    }

    public static <V, T> PageVO<T> getPageResVO(Class<T> targetType, Supplier<IPage<V>> supplier) {
        IPage<V> iPage = supplier.get();
        PageVO<T> res = new PageVO<>();
        List<T> resVOList = BeanUtil.copyToList(iPage.getRecords(), targetType, CopyOptions.create().ignoreCase());
        BeanUtil.copyProperties(iPage, res);
        res.setRecords(resVOList);
        res.setPages(iPage.getPages());
        return res;
    }
}
