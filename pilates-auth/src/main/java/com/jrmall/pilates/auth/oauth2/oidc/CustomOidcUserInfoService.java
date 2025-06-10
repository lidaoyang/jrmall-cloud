package com.jrmall.pilates.auth.oauth2.oidc;

import com.jrmall.pilates.system.api.UserAuthApi;
import com.jrmall.pilates.system.dto.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自定义 OIDC 用户信息服务
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Service
@Slf4j
public class CustomOidcUserInfoService {

    @DubboReference
    private  UserAuthApi userAuthApi;
    public CustomOidcUserInfo loadUserByUsername(String username) {
        UserAuthInfo userAuthInfo;
        try {
            userAuthInfo = userAuthApi.getUserAuthInfo(username);
            if (userAuthInfo == null) {
                return null;
            }
            return new CustomOidcUserInfo(createUser(userAuthInfo));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return null;
        }
    }

    private Map<String, Object> createUser(UserAuthInfo userAuthInfo) {
        return CustomOidcUserInfo.customBuilder()
                .username(userAuthInfo.getUsername())
                .nickname(userAuthInfo.getNickname())
                .status(userAuthInfo.getStatus())
                .phoneNumber(userAuthInfo.getMobile())
                .email(userAuthInfo.getEmail())
                .profile(userAuthInfo.getAvatar())
                .build()
                .getClaims();
    }

}
