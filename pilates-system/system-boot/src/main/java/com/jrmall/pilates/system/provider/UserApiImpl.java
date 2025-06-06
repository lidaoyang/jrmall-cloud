package com.jrmall.pilates.system.provider;

import com.jrmall.pilates.system.api.UserAuthApi;
import com.jrmall.pilates.system.dto.UserAuthInfo;
import com.jrmall.pilates.system.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 用户接口提供者
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 10:13
 */
@Slf4j
@DubboService
public class UserApiImpl implements UserAuthApi {

    @Resource
    private SysUserService sysUserService;


    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        return sysUserService.getUserAuthInfo(username);
    }
}
