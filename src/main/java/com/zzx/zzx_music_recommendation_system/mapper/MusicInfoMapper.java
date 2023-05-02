package com.zzx.zzx_music_recommendation_system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzx.zzx_music_recommendation_system.vo.CommentVO;
import com.zzx.zzx_music_recommendation_system.vo.RankResVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Mapper
public interface MusicInfoMapper extends BaseMapper<MusicInfo> {

    List<RankResVO> getRank(@Param("musicIds") List<Long> musicIds);

    IPage<RankResVO> getMusicsPage(Page<RankResVO> page,
                                   @Param("musicIds") List<Long> musicIds);

}
