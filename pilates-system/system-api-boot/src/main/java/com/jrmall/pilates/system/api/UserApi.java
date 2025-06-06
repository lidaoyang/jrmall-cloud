package com.jrmall.pilates.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.system.model.form.UserForm;
import com.jrmall.pilates.system.model.form.UserRegisterForm;
import com.jrmall.pilates.system.model.query.UserPageQuery;
import com.jrmall.pilates.system.model.vo.UserExportVO;
import com.jrmall.pilates.system.model.vo.UserInfoVO;
import com.jrmall.pilates.system.model.vo.UserPageVO;
import com.jrmall.pilates.system.model.vo.UserProfileVO;

import java.util.List;

/**
 * 用户API
 */
public interface UserApi {

    /**
     * 用户分页列表
     *
     * @return {@link IPage <UserPageVO>}
     */
    IPage<UserPageVO> getUserPage(UserPageQuery queryParams);

    /**
     * 获取用户表单数据
     *
     * @param userId 用户ID
     * @return {@link UserForm}
     */
    UserForm getUserFormData(Long userId);

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return {@link Boolean}
     */
    boolean saveUser(UserForm userForm);

    /**
     * 修改用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     * @return {@link Boolean}
     */
    boolean updateUser(Long userId, UserForm userForm);

    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     * @return {@link Boolean}
     */
    boolean deleteUsers(String idsStr);

    /**
     * 修改用户密码
     *
     * @param userId   用户ID
     * @param password 用户密码
     * @return {@link Boolean}
     */
    boolean updatePassword(Long userId, String password);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 用户状态(1:启用;0:禁用)
     * @return {@link Boolean}
     */
    boolean updateStatus(Long userId, Integer status);

    /**
     * 获取登录用户信息
     *
     * @return {@link UserInfoVO}
     */
    UserInfoVO getCurrentUserInfo();

    /**
     * 获取导出用户列表
     *
     * @param queryParams 查询参数
     * @return {@link List < UserExportVO >}
     */
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);

    /**
     * 注册用户
     *
     * @param userRegisterForm 用户注册表单对象
     * @return {@link Boolean}
     */
    boolean registerUser(UserRegisterForm userRegisterForm);

    /**
     * 发送注册短信验证码
     *
     * @param mobile 手机号
     * @return {@link Boolean} 是否发送成功
     */
    boolean sendRegistrationSmsCode(String mobile);

    /**
     * 获取用户个人中心信息
     *
     * @return {@link UserProfileVO}
     */
    UserProfileVO getUserProfile();

    /**
     * 退出登录
     *
     * @return {@link Boolean}
     */
    boolean logout();
}
