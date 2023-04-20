package com.zzx.zzx_music_recommendation_system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zzx.zzx_music_recommendation_system.dao.LikeInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.MusicInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.LikeInfo;
import com.zzx.zzx_music_recommendation_system.entity.MusicInfo;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.login.UserInfoUtil;
import com.zzx.zzx_music_recommendation_system.mapper.LikeInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.LikeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.utils.DateUtils;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import com.zzx.zzx_music_recommendation_system.utils.SshSshdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

import static com.zzx.zzx_music_recommendation_system.constant.StringConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class LikeInfoServiceImpl extends ServiceImpl<LikeInfoMapper, LikeInfo> implements LikeInfoService {

    @Autowired
    private LikeInfoDao likeInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MusicInfoDao musicInfoDao;

    @Override
    public boolean updateMouthRank() {
        LocalDateTime time = LocalDateTime.now().minusMonths(1);
        try {
            List<String> mouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), new TypeReference<List<String>>() {});
            if (mouth != null && mouth.size() >= 1) {
                return true;
            }
            List<String> lastMouth = redisUtils.getList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time.minusMonths(1)), new TypeReference<List<String>>() {});
            if (lastMouth != null && lastMouth.size() >= 1) {
                redisUtils.deleteList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time.minusMonths(1)));
            }
            List<String> list = likeInfoDao.updateMouthRank(DateUtils.getStartTime(time), DateUtils.getEndTime(time));
            redisUtils.setList(RedisUtils.Type.MOUTH_RANKING, DateUtils.getDateyyyyMMStr(time), list);
        } catch (Exception e) {
            log.error("redis出错", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateDayRank() {
        LocalDateTime time = LocalDateTime.now().minusDays(1);
        try {
            List<String> day = redisUtils.getList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time), new TypeReference<List<String>>() {});
            if (day != null && day.size() >= 1) {
                return true;
            }
            List<String> lastDay = redisUtils.getList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time.minusMonths(1)), new TypeReference<List<String>>() {});
            if (lastDay != null && lastDay.size() >= 1) {
                redisUtils.deleteList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time.minusDays(1)));
            }
            List<String> list = likeInfoDao.updateDayRank(DateUtils.getStartTime(time), DateUtils.getEndTime(time));
            redisUtils.setList(RedisUtils.Type.DAY_RANKING, DateUtils.getDateyyyyMMddStr(time), list);
        } catch (Exception e) {
            log.error("redis出错", e);
            return false;
        }
        return true;
    }

    @Override
    public MusicInfo saveLikeInfo(Long musicId, SongListTypeEnum songListTypeEnum) {
        MusicInfo musicInfo = musicInfoDao.getById(musicId);
        LikeInfo likeInfo = new LikeInfo();
        likeInfo.setUserId(UserInfoUtil.getUserId());
        likeInfo.setMusicId(musicId);
        likeInfo.setLikeType(songListTypeEnum.getCode());
        CommonUtils.fillWhenSave(likeInfo);
        likeInfoDao.save(likeInfo);
        return musicInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public MusicInfo saveDownloadLikeInfo(Long musicId, HttpServletResponse response) {
        MusicInfo musicInfo = saveLikeInfo(musicId, SongListTypeEnum.DOWNLOAD);

        SshSshdUtils sshSshdUtils = new SshSshdUtils(IP, ROOT, Integer.parseInt(PORT), PASSWORD);
        InputStream inputStream = sshSshdUtils.sftpGetFile(musicInfo.getMusicContent());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=music.mp3");
        try (OutputStream outputStream = response.getOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return musicInfo;
    }

    @Override
    public void deleteLikeInfo(Long musicId) {
        likeInfoDao.remove(new QueryWrapper<LikeInfo>().lambda()
                .eq(LikeInfo::getMusicId, musicId)
                .eq(LikeInfo::getCreateUserId, UserInfoUtil.getUserId())
                .eq(LikeInfo::getLikeType, SongListTypeEnum.LIKE.getCode()));
    }
}
