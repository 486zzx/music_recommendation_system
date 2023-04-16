package com.zzx.zzx_music_recommendation_system.login;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt登录授权过滤器
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private LoginUserCache loginUserCache;

    public static final String TOKEN_HEADER = "token";


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws ServletException,
            IOException {

        String authToken = UserInfoUtil.obtainAuthorization(request, TOKEN_HEADER);
        log.info("token:{}", authToken);
        //存在token
        if (null != authToken) {
            String username = jwtTokenUtil.getUserNameFormToken(authToken);
            UserDetails user = loginUserCache.get(authToken);
            if (user == null || null == username || !username.equals(user.getUsername())) {
                throw new AccessDeniedException("token 错误");
            }
            // 重新设置，刷新过期时间
            loginUserCache.set(authToken, user);

            //token中存在用户名但未登录
            if (null == SecurityContextHolder.getContext().getAuthentication()) {
                //验证token是否有效，重新设置用户对象
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user,
                                null, user.getAuthorities());
                authentication.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        chain.doFilter(request, response);
    }
}
