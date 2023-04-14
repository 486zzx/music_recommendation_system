package com.zzx.zzx_music_recommendation_system.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限时，自定义返回结果类
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ResVO<Object> error = ResVO.fail(403, "未授权或未携带token");
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}