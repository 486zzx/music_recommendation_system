package com.zzx.zzx_music_recommendation_system.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzx.zzx_music_recommendation_system.dao.UserInfoDao;
import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import com.zzx.zzx_music_recommendation_system.enums.UserRoleEnum;
import com.zzx.zzx_music_recommendation_system.login.JwtAuthenticationTokenFilter;
import com.zzx.zzx_music_recommendation_system.login.RestAuthorizationEntryPoint;
import com.zzx.zzx_music_recommendation_system.login.RestfulAccessDeniedHandler;
import com.zzx.zzx_music_recommendation_system.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @DESCRIPTION:
 * @USER: zzx
 * @DATE: 2023/4/16 16:48
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    RestAuthorizationEntryPoint restAuthorizationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserInfo userInfo = userInfoDao.getOne( new QueryWrapper<UserInfo>().lambda()
                        .eq(UserInfo::getIsDelete, 1));
                if (null != userInfo) {
                    return User.withUsername(username)
                            .roles(UserRoleEnum.getMessageByCode(userInfo.getUserType()))
                            .password(userInfo.getUserPassword())
                            .build();
                }
                throw new MyException(400, "用户名或密码不正确！");
            }
        };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用JWT，不需要csrf
        http.csrf()
                .disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //所有请求都要求认证
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                //禁用缓存
                .cacheControl();
        //添加jwt 登录授权过滤器
        http.cors();
        http.addFilterBefore(jwtAuthenticationTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行静态资源
        web.ignoring().antMatchers(
                "/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}
