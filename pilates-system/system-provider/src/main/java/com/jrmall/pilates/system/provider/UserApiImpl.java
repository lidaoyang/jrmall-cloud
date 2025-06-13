package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.system.api.UserApi;
import com.jrmall.pilates.system.model.form.UserForm;
import com.jrmall.pilates.system.model.form.UserRegisterForm;
import com.jrmall.pilates.system.model.query.UserPageQuery;
import com.jrmall.pilates.system.model.vo.UserExportVO;
import com.jrmall.pilates.system.model.vo.UserInfoVO;
import com.jrmall.pilates.system.model.vo.UserPageVO;
import com.jrmall.pilates.system.model.vo.UserProfileVO;
import com.jrmall.pilates.system.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 用户接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 10:13
 */
@DubboService
public class UserApiImpl implements UserApi {

    @Resource
    private SysUserService userService;


    @Override
    public IPage<UserPageVO> getUserPage(UserPageQuery queryParams) {
        return userService.getUserPage(queryParams);
    }

    @Override
    public UserForm getUserFormData(Long userId) {
        return userService.getUserFormData(userId);
    }

    @Override
    public boolean saveUser(UserForm userForm) {
        return userService.saveUser(userForm);
    }

    @Override
    public boolean updateUser(Long userId, UserForm userForm) {
        return userService.updateUser(userId, userForm);
    }

    @Override
    public boolean deleteUsers(String idsStr) {
        return userService.deleteUsers(idsStr);
    }

    @Override
    public boolean updatePassword(Long userId, String password) {
        return userService.updatePassword(userId, password);
    }

    @Override
    public boolean updateStatus(Long userId, Integer status) {
        return userService.updateStatus(userId, status);
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }

    @Override
    public List<UserExportVO> listExportUsers(UserPageQuery queryParams) {
        return userService.listExportUsers(queryParams);
    }

    @Override
    public boolean registerUser(UserRegisterForm userRegisterForm) {
        return userService.registerUser(userRegisterForm);
    }

    @Override
    public boolean sendRegistrationSmsCode(String mobile) {
        return userService.sendRegistrationSmsCode(mobile);
    }

    @Override
    public UserProfileVO getUserProfile() {
        return userService.getUserProfile();
    }

}
