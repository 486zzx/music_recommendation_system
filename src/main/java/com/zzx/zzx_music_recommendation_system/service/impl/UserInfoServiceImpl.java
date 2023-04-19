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
import com.zzx.zzx_music_recommendation_system.login.JwtTokenUtil;
import com.zzx.zzx_music_recommendation_system.login.LoginUserCache;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private LoginUserCache loginUserCache;

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

        //查看用户是否存在,存在则报错
        if (userInfoDao.isEmailExist(reqVO.getUserEmail())) {
            throw new MyException("该邮箱已注册");
        }

        //注册用户
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(reqVO.getUserName());
        userInfo.setUserPassword(new BCryptPasswordEncoder().encode(reqVO.getUserPassword()));
        userInfo.setUserEmail(reqVO.getUserEmail());
        CommonUtils.fillWhenSave(userInfo);
        userInfoDao.save(userInfo);

        //歌单表(当前播放)
        SongList songList = new SongList();
        songList.setUserId(userInfo.getUserId());
        songList.setSongListType(SongListTypeEnum.NOW_PLAY.getCode());
        CommonUtils.fillWhenSave(songList);
        songListDao.save(songList);

        //用户歌单表
        UserSongList u = new UserSongList();
        u.setSongListId(songList.getSongListId());
        CommonUtils.fillWhenSave(u);
        userSongListDao.save(u);
    }

    @Override
    public String login(LoginReqVO reqVO) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(reqVO.getUserEmail());
        if (null == userDetails || ! passwordEncoder.matches(reqVO.getUserPassword(),
                userDetails.getPassword())) {
            throw new MyException(301, "用户名或密码错误");
        }
        if (!userDetails.isEnabled()) {
            throw new MyException(302, "账号被禁用，请联系管理员");
        }
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        loginUserCache.set(token, userDetails);
        userInfoDao.login(reqVO.getUserEmail());
        return token;
    }


    private void sendEmail(String email, String code) {
        String subject="验证码验证";
        String content="非常高兴您能加入我们，您本次的验证码为："+code+"\n\n"+"再次感谢您的加入";
        MailUtil.send(email, subject, content, false);
    }


}
