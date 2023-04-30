package com.zzx.zzx_music_recommendation_system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.zzx_music_recommendation_system.entity.CommentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzx.zzx_music_recommendation_system.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Mapper
public interface CommentInfoMapper extends BaseMapper<CommentInfo> {

    IPage<CommentVO> queryForPage(Page<CommentVO> page,
                                  @Param("musicId") Long musicId);

}
