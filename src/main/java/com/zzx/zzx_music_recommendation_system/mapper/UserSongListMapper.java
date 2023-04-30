package com.zzx.zzx_music_recommendation_system.mapper;

import com.zzx.zzx_music_recommendation_system.entity.UserSongList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzx.zzx_music_recommendation_system.vo.GetHotSongListResVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Mapper
public interface UserSongListMapper extends BaseMapper<UserSongList> {

    List<GetHotSongListResVO> getHotSongList();

}
