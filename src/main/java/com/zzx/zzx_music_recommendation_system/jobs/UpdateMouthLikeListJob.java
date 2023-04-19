package com.zzx.zzx_music_recommendation_system.jobs;

import com.zzx.zzx_music_recommendation_system.constant.DateTimeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/18 19:58
 */
@Slf4j
public class UpdateMouthLikeListJob {

    @Scheduled(cron = DateTimeConstants.CRON_MOUTH_TIME_FORMAT)
    public void deleteTimeoutServiceRecord() {
//        log.info("更新月榜开始");
//        LocalDateTime target = LocalDateTime.now().withSecond(0).withNano(0).minusDays(7);
//        List<SysRecordPO> list = sysRecordDAO.list();
//        List<Long> recordIds = list.stream().filter(l -> l.getUpdateTime().compareTo(target) < 0).map(SysRecordPO::getEid).collect(Collectors.toList());
//        BusinessAssertUtils.isTrue(sysRecordDAO.removeByIds(recordIds), "删除过期服务记录出现异常!");
//        log.info("开始删除过期文件!过期文件数量为{}", recordIds.size());
//        recordIds.forEach(this::deleteLocalFile);
//        log.info("删除过期文件结束!");
    }

}
