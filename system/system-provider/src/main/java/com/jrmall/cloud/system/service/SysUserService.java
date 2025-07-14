package com.jrmall.cloud.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jrmall.cloud.common.base.Option;
import com.jrmall.cloud.system.dto.UserAuthInfo;
import com.jrmall.cloud.system.model.entity.SysUser;
import com.jrmall.cloud.system.model.form.*;
import com.jrmall.cloud.system.model.query.UserPageQuery;
import com.jrmall.cloud.system.model.vo.CurrentUserVO;
import com.jrmall.cloud.system.model.vo.UserExportVO;
import com.jrmall.cloud.system.model.vo.UserPageVO;
import com.jrmall.cloud.system.model.vo.UserProfileVO;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author haoxr
 * @since 2022/1/14
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 用户分页列表
     *
     * @return {@link IPage<UserPageVO>} 用户分页列表
     */
    IPage<UserPageVO> getUserPage(UserPageQuery queryParams);

    /**
     * 获取用户表单数据
     *
     * @param userId 用户ID
     * @return {@link UserForm} 用户表单数据
     */
    UserForm getUserFormData(Long userId);


    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return {@link Boolean} 是否新增成功
     */
    boolean saveUser(UserForm userForm);

    /**
     * 修改用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     * @return {@link Boolean} 是否修改成功
     */
    boolean updateUser(Long userId, UserForm userForm);


    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     * @return {@link Boolean} 是否删除成功
     */
    boolean deleteUsers(String idsStr);

    /**
     * 修改用户状态
     *
     * @param userId   用户ID
     * @param status 用户状态
     * @return {@link Boolean} 是否修改成功
     */
    boolean updateUserStatus(Long userId, Integer status);

    /**
     * 获取登录用户信息
     *
     * @return {@link CurrentUserVO} 登录用户信息
     */
    CurrentUserVO getCurrentUserInfo();

    /**
     * 获取导出用户列表
     *
     * @param queryParams 查询参数
     * @return {@link List<UserExportVO>} 导出用户列表
     */
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);

    /**
     * 注册用户
     *
     * @param userRegisterForm 注册表单数据
     * @return {@link Boolean} 是否注册成功
     */
    boolean registerUser(UserRegisterForm userRegisterForm);

    /**
     * 获取个人中心用户信息
     *
     * @return {@link UserProfileVO} 个人中心用户信息
     */
    UserProfileVO getUserProfile();

    /**
     * 修改个人中心用户信息
     *
     * @param formData 表单数据
     * @return {@link Boolean} 是否修改成功
     */
    boolean updateUserProfile(UserProfileForm formData);

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param data   修改密码表单数据
     * @return {@link Boolean} 是否修改成功
     */
    boolean changePassword(Long userId, PasswordUpdateForm data);

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 重置后的密码
     * @return {@link Boolean} 是否重置成功
     */
    boolean resetPassword(Long userId, String password);

    /**
     * 发送短信验证码(绑定或更换手机号)
     *
     * @param mobile 手机号
     * @return {@link Boolean} 是否发送成功
     */
    boolean sendMobileCode(String mobile);

    /**
     * 修改当前用户手机号
     *
     * @param data 表单数据
     * @return {@link Boolean} 是否修改成功
     */
    boolean bindOrChangeMobile(MobileUpdateForm data);

    /**
     * 发送邮箱验证码(绑定或更换邮箱)
     *
     * @param email 邮箱
     */
    void sendEmailCode(String email);

    /**
     * 绑定或更换邮箱
     *
     * @param data 表单数据
     * @return {@link Boolean} 是否绑定成功
     */
    boolean bindOrChangeEmail(EmailUpdateForm data);

    /**
     * 获取用户选项列表
     *
     * @return {@link List<Option<String>>} 用户选项列表
     */
    List<Option<String>> listUserOptions();

    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return 用户认证信息 {@link UserAuthInfo}
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * 退出登录
     *
     * @return {@link Boolean} 是否退出成功
     */
    boolean logout();
}
