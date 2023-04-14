package com.zzx.zzx_music_recommendation_system.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzx.zzx_music_recommendation_system.vo.ResVO;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效时访问接口时，自定义的返回结果
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse
            response, AuthenticationException authException) throws IOException,
            ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ResVO<Object> error = ResVO.fail(409, "未登录或未携带token");
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}