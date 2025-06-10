package com.jrmall.pilates.auth.service;

import cn.hutool.core.lang.Assert;
import com.jrmall.pilates.auth.model.LoginUserInfo;
import com.jrmall.pilates.auth.model.SysUserDetails;
import com.jrmall.pilates.common.enums.StatusEnum;
import com.jrmall.pilates.system.api.UserAuthApi;
import com.jrmall.pilates.system.dto.UserAuthInfo;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * 系统用户信息加载实现类
 *
 * @author haoxr
 * @since 3.0.0
 */
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    @DubboReference
    private UserAuthApi userAuthApi;

    /**
     * 根据用户名获取用户信息(用户名、密码和角色权限)
     * <p>
     * 用户名、密码用于后续认证，认证成功之后将权限授予用户
     *
     * @param username 用户名
     * @return {@link  SysUserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAuthInfo userAuthInfo = userAuthApi.getUserAuthInfo(username);

        Assert.isTrue(userAuthInfo != null, "用户不存在");

        if (!StatusEnum.ENABLE.getValue().equals(userAuthInfo.getStatus())) {
            throw new DisabledException("该账户已被禁用!");
        }

        return new SysUserDetails(userAuthInfo);
    }


    public LoginUserInfo getLoginUserInfo() {
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setId(123L);
        return loginUserInfo;
    }
}
