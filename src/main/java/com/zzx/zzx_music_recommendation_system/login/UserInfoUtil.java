package com.zzx.zzx_music_recommendation_system.login;

import com.zzx.zzx_music_recommendation_system.entity.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
* description:
* @author liulu
* @since 2022/4/20 13:57
* @version 1.0
**/
public class UserInfoUtil {

    /**
     * 查询当前用户详细信息
     *
     * @return 用户详细信息
     */
    public static UserInfo getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return (UserInfo) authentication.getPrincipal();
    }

    /**
     * 查询当前用户Id
     *
     * @return 当前用户Id
     */
    public static Long getUserId() {
        return getUserInfo().getUserId();
    }


    /**
     * 从请求中，获得认证 Token
     *
     * @param request 请求
     * @param header  认证 Token 对应的 Header 名字
     * @return 认证 Token
     */
    public static String obtainAuthorization(HttpServletRequest request, String header) {

        // 从token header获取
        String tokenFromHeader = request.getHeader(header);
        if (StringUtils.hasText(tokenFromHeader)) {
            return tokenFromHeader;
        }

        // 从Bearer 获取
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf("Bearer ");
        if (index == -1) {
            // 未找到
            return null;
        }
        return authorization.substring(index + 7).trim();
    }
}

