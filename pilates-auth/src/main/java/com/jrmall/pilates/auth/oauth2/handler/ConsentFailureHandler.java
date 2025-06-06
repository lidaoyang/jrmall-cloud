package com.jrmall.pilates.auth.oauth2.handler;

import cn.hutool.json.JSONUtil;
import com.jrmall.pilates.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;

import java.io.IOException;

/**
 * 授权确认失败处理
 *
 * @author haoxr
 * @since 3.0.0
 */
@Slf4j
@RequiredArgsConstructor
public class ConsentFailureHandler implements AuthenticationFailureHandler {

    private final String consentPageUri;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 获取具体的异常
        OAuth2AuthenticationException authenticationException = (OAuth2AuthenticationException) exception;
        OAuth2Error error = authenticationException.getError();

        String message;
        if (authentication == null) {
            message = "登录已失效";
        } else {
            message = error.toString();
        }
        // 绝对路径，表示前后端分离，返回json异常
        if(UrlUtils.isAbsoluteUrl(consentPageUri)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONUtil.toJsonStr(Result.failed(message)));
            return;
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }
}
