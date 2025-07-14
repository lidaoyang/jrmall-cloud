package com.jrmall.cloud.auth.oauth2.handler;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.UrlUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author haoxr
 * @since 2024/3/11
 */
@Slf4j
public class LoginTargetAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LoginTargetAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String loginFormUrl = this.determineUrlToUseForThisRequest(request, response, authException);

        if (UrlUtils.isAbsoluteUrl(loginFormUrl)) {
            // 绝对路径，标识前后端分离，重定向到登录页面

            StringBuffer requestUrl = request.getRequestURL();
            String queryString = request.getQueryString();
            if (StrUtil.isNotBlank(queryString)) {
                requestUrl.append("?").append(queryString);
            }
            String targetParameter = URLEncoder.encode(requestUrl.toString(), StandardCharsets.UTF_8);
            String targetUrl = loginFormUrl + "?target=" + targetParameter;
            log.info("未授权重定向到前后端分离登录页面：{}", targetUrl);
            this.redirectStrategy.sendRedirect(request, response, targetUrl);
        } else {
            super.commence(request, response, authException);
        }

    }


}
