package com.zzx.zzx_music_recommendation_system.mapper;

import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
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
public interface LikeInfoMapper extends BaseMapper<LikeInfo> {

    List<String> updateMouthRank(@Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);


}
