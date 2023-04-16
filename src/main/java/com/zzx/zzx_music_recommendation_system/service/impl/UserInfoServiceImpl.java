package com.zzx.zzx_music_recommendation_system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.SongListDao;
import com.zzx.zzx_music_recommendation_system.dao.UserInfoDao;
import com.zzx.zzx_music_recommendation_system.dao.UserSongListDao;
import com.zzx.zzx_music_recommendation_system.entity.SongList;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.entity.UserSongList;
import com.zzx.zzx_music_recommendation_system.enums.SongListTypeEnum;
import com.zzx.zzx_music_recommendation_system.mapper.UserInfoMapper;
import com.zzx.zzx_music_recommendation_system.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzx.zzx_music_recommendation_system.utils.CommonUtils;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import com.zzx.zzx_music_recommendation_system.utils.RedisUtils;
import com.zzx.zzx_music_recommendation_system.vo.LoginReqVO;
import com.zzx.zzx_music_recommendation_system.vo.RegisterReqVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzx
 * @since 2023-03-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SongListDao songListDao;

    @Autowired
    private UserSongListDao userSongListDao;

    @Override
    public void sendValidateCode(String email, HttpServletRequest request) {
        //1.避免重复申请
        if (tooQuickly(request, 1)) {
            throw new MyException("邮件发送频率过快");
        }
        //2.验证是否存在
        if (userInfoDao.isEmailExist(email)) {
            throw new MyException("该邮件已存在");
        }
        //3.发送验证码
        String code=(int)(Math.random()*10000)+"";
        try {
            sendEmail(email, code);
            redisUtils.set(RedisUtils.Type.EMAIL_VALIDATE_CODE, email, code);
        } catch (Exception e) {
            throw new MyException("邮箱发送失败，检查邮箱是否正确");
        }
    }

    @Override
    public boolean tooQuickly(HttpServletRequest request, int minutes) {
        //第一次操作
        if(request.getSession().getAttribute("lastTime")==null) {
            request.getSession().setAttribute("lastTime", new SimpleDateFormat("mm").format(new Date()));
            return false;
        }
        //第二次及其以上的操作
        String lastMinute=(String) request.getSession().getAttribute("lastTime");
        String curMinute=new SimpleDateFormat("mm").format(new Date());

        if( Math.abs(Integer.parseInt(curMinute)- Integer.valueOf(lastMinute)) <=minutes) {
            return true;
        }
        request.getSession().setAttribute("lastTime",curMinute);
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void register(RegisterReqVO reqVO) {
        String code = redisUtils.get(RedisUtils.Type.EMAIL_VALIDATE_CODE, reqVO.getUserEmail());
        if (!StrUtil.equals(reqVO.getCode(), code)) {
            throw new MyException("验证码错误");
        }

        //注册用户
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(reqVO, userInfo);
        CommonUtils.fillWhenSave(userInfo);
        userInfoDao.save(userInfo);

        //歌单表
        List<SongList> songLists = new ArrayList<>();
        for (SongListTypeEnum e : SongListTypeEnum.values()) {
            if (e.getCode().equals(SongListTypeEnum.COMMON_SONG_LIST.getCode())) {
                continue;
            }
            SongList songList = new SongList();
            songList.setUserId(userInfo.getUserId());
            songList.setSongListType(e.getCode());
            CommonUtils.fillWhenSave(songList);
            songLists.add(songList);
        }
        songListDao.saveBatch(songLists);

        //用户歌单表
        List<UserSongList> userSongLists = new ArrayList<>();
        for (SongList s : songLists) {
            UserSongList u = new UserSongList();
            u.setSongListId(s.getSongListId());
            CommonUtils.fillWhenSave(u);
            userSongLists.add(u);
        }
        userSongListDao.saveBatch(userSongLists);
    }

    @Override
    public void login(LoginReqVO reqVO) {
        UserInfo userInfo = userInfoDao.isUserExist(reqVO.getUserEmail(), reqVO.getUserPassword());
        //
    }


    private void sendEmail(String email, String code) {
        String subject="验证码验证";
        String content="非常高兴您能加入我们，您本次的验证码为："+code+"\n\n"+"再次感谢您的加入";
        MailUtil.send(email, subject, content, false);
    }


}
