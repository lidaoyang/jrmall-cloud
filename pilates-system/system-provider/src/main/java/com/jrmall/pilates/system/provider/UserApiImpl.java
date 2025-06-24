package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.UserApi;
import com.jrmall.pilates.system.model.form.*;
import com.jrmall.pilates.system.model.query.UserPageQuery;
import com.jrmall.pilates.system.model.vo.*;
import com.jrmall.pilates.system.service.SysUserService;
import jakarta.annotation.Resource;
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
    public boolean updateUserStatus(Long userId, Integer status) {
        return userService.updateUserStatus(userId, status);
    }

    @Override
    public CurrentUserVO getCurrentUserInfo() {
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
    public UserProfileVO getUserProfile() {
        return userService.getUserProfile();
    }

    @Override
    public boolean updateUserProfile(UserProfileForm formData) {
        return userService.updateUserProfile(formData);
    }

    @Override
    public boolean changePassword(Long userId, PasswordUpdateForm data) {
        return userService.changePassword(userId, data);
    }

    @Override
    public boolean resetPassword(Long userId, String password) {
        return userService.resetPassword(userId, password);
    }

    @Override
    public boolean sendMobileCode(String mobile) {
        return userService.sendMobileCode(mobile);
    }

    @Override
    public boolean bindOrChangeMobile(MobileUpdateForm data) {
        return userService.bindOrChangeMobile(data);
    }

    @Override
    public void sendEmailCode(String email) {
        userService.sendEmailCode(email);
    }

    @Override
    public boolean bindOrChangeEmail(EmailUpdateForm data) {
        return userService.bindOrChangeEmail(data);
    }

    @Override
    public List<Option<String>> listUserOptions() {
        return userService.listUserOptions();
    }
}
