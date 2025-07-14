package com.jrmall.cloud.auth.oauth2.handler;

import com.alibaba.fastjson2.JSON;
import com.jrmall.cloud.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 授权确认成功响应处理
 *
 * @author haoxr
 * @since 3.0.0
 */

@RequiredArgsConstructor
public class ConsentSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 授权确认页面
     */
    private final String consentPageUri;

    private final RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 获取回调地址
        OAuth2AuthorizationCodeRequestAuthenticationToken authorizationCodeRequestAuthentication = (OAuth2AuthorizationCodeRequestAuthenticationToken) authentication;
        String redirectUri = authorizationCodeRequestAuthentication.getRedirectUri();
        OAuth2AuthorizationCode authorizationCode = authorizationCodeRequestAuthentication.getAuthorizationCode();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam(OAuth2ParameterNames.CODE, authorizationCode.getTokenValue());

        String state = authorizationCodeRequestAuthentication.getState();
        if (StringUtils.hasText(state)) {
            uriBuilder.queryParam(OAuth2ParameterNames.STATE, state);
        }
         redirectUri = uriBuilder.build(true).toUriString();

        // 如果授权页面是绝对地址，表示是前后端分离，返回json
        if(UrlUtils.isAbsoluteUrl(consentPageUri)) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(JSON.toJSONString(Result.success(redirectUri)));
            return;
        }

        // 如果是相对地址，表示是后端渲染，重定向到授权页面
        this.redirectStrategy.sendRedirect(request, response, redirectUri);
    }
}
