package com.jrmall.pilates.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.common.constant.GlobalConstants;
import com.jrmall.pilates.common.constant.RedisConstants;
import com.jrmall.pilates.common.constant.SystemConstants;
import com.jrmall.pilates.common.dubbo.util.RpcUtil;
import com.jrmall.pilates.common.email.service.MailService;
import com.jrmall.pilates.common.exception.ProviderAccessDeniedException;
import com.jrmall.pilates.common.exception.ProviderException;
import com.jrmall.pilates.common.redis.util.RedisUtil;
import com.jrmall.pilates.common.result.ResultCode;
import com.jrmall.pilates.common.sms.property.AliyunSmsProperties;
import com.jrmall.pilates.common.sms.service.SmsService;
import com.jrmall.pilates.system.converter.UserConverter;
import com.jrmall.pilates.system.dto.UserAuthInfo;
import com.jrmall.pilates.system.enums.DictCodeEnum;
import com.jrmall.pilates.system.mapper.SysUserMapper;
import com.jrmall.pilates.system.model.bo.UserBO;
import com.jrmall.pilates.system.model.bo.UserFormBO;
import com.jrmall.pilates.system.model.bo.UserProfileBO;
import com.jrmall.pilates.system.model.entity.SysDictItem;
import com.jrmall.pilates.system.model.entity.SysUser;
import com.jrmall.pilates.system.model.form.*;
import com.jrmall.pilates.system.model.query.UserPageQuery;
import com.jrmall.pilates.system.model.vo.*;
import com.jrmall.pilates.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户业务实现类
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    private final SysUserRoleService userRoleService;

    private final SysRoleService roleService;

    private final SysRoleMenuService roleMenuService;

    private final SysDictItemService dictItemService;

    private final UserConverter userConverter;

    private final SmsService smsService;

    private final MailService mailService;

    private final AliyunSmsProperties aliyunSmsProperties;

    private final RedisUtil redisUtil;

    /**
     * 获取用户分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage<UserPageVO>} 用户分页列表
     */
    @Override
    public IPage<UserPageVO> getUserPage(UserPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<UserBO> page = new Page<>(pageNum, pageSize);

        boolean isRoot = RpcUtil.isRoot();
        queryParams.setIsRoot(isRoot);

        // 查询数据
        Page<UserBO> userPage = this.baseMapper.getUserPage(page, queryParams);
        // 实体转换
        return userConverter.toPageVo(userPage);
    }


    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return {@link UserForm}
     */
    @Override
    public UserForm getUserFormData(Long userId) {
        UserFormBO userFormBO = this.baseMapper.getUserDetail(userId);
        // 实体转换po->form
        return userConverter.bo2Form(userFormBO);
    }

    /**
     * 新增用户
     *
     * @param userForm 用户表单对象
     * @return true|false
     */
    @Override
    public boolean saveUser(UserForm userForm) {

        String username = userForm.getUsername();

        long count = this.count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        Assert.isTrue(count == 0, "用户名已存在");

        // 实体转换 form->entity
        SysUser entity = userConverter.toEntity(userForm);

        // 设置默认加密密码
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // 新增用户
        boolean result = this.save(entity);

        if (result) {
            // 保存用户角色
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }

    /**
     * 更新用户
     *
     * @param userId   用户ID
     * @param userForm 用户表单对象
     * @return true|false 是否更新成功
     */
    @Override
    @Transactional
    public boolean updateUser(Long userId, UserForm userForm) {

        String username = userForm.getUsername();

        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username)
                .ne(SysUser::getId, userId)
        );
        Assert.isTrue(count == 0, "用户名已存在");

        // form -> entity
        SysUser entity = userConverter.toEntity(userForm);

        // 修改用户
        boolean result = this.updateById(entity);

        if (result) {
            // 保存用户角色
            userRoleService.saveUserRoles(entity.getId(), userForm.getRoleIds());
        }
        return result;
    }

    /**
     * 删除用户
     *
     * @param idsStr 用户ID，多个以英文逗号(,)分割
     * @return true/false 是否删除成功
     */
    @Override
    public boolean deleteUsers(String idsStr) {
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong).
                collect(Collectors.toList());
        return this.removeByIds(ids);

    }

    @Override
    public boolean updateUserStatus(Long userId, Integer status) {
        return this.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getStatus, status)
        );
    }

    /**
     * 获取登录用户信息
     *
     * @return {@link UserInfoVO}   用户信息
     */
    @Override
    public CurrentUserVO getCurrentUserInfo() {
        // 登录用户entity
        SysUser user = this.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, RpcUtil.getUsername())
                .select(
                        SysUser::getId,
                        SysUser::getNickname,
                        SysUser::getAvatar
                )
        );
        // entity->VO
        CurrentUserVO userInfoVO = userConverter.toCurrentUserVo(user);

        // 获取用户角色集合
        Set<String> roles = RpcUtil.getRoles();
        userInfoVO.setRoles(roles);

        // 获取用户权限集合
        if (CollectionUtil.isNotEmpty(roles)) {
            Set<String> perms = roleMenuService.getRolePermsFormCache(roles);
            userInfoVO.setPerms(perms);
        }

        return userInfoVO;
    }

    @Override
    public List<UserExportVO> listExportUsers(UserPageQuery queryParams) {
        boolean isRoot = RpcUtil.isRoot();
        queryParams.setIsRoot(isRoot);

        List<UserExportVO> exportUsers = this.baseMapper.listExportUsers(queryParams);
        if (CollectionUtil.isNotEmpty(exportUsers)) {
            // 获取性别的字典项
            Map<String, String> genderMap = dictItemService.list(
                            new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictCode,
                                    DictCodeEnum.GENDER.getValue())
                    ).stream()
                    .collect(Collectors.toMap(SysDictItem::getValue, SysDictItem::getLabel)
                    );

            exportUsers.forEach(item -> {
                String gender = item.getGender();
                if (StrUtil.isBlank(gender)) {
                    return;
                }

                // 判断map是否为空
                if (genderMap.isEmpty()) {
                    return;
                }

                item.setGender(genderMap.get(gender));
            });
        }
        return exportUsers;
    }

    /**
     * 注册用户
     *
     * @param userRegisterForm 用户注册表单对象
     * @return true|false 是否注册成功
     */
    @Override
    public boolean registerUser(UserRegisterForm userRegisterForm) {

        String mobile = userRegisterForm.getMobile();
        String code = userRegisterForm.getCode();
        // 校验验证码
        String cacheCode = redisUtil.get(RedisConstants.Captcha.MOBILE_CODE + mobile);
        if (!StrUtil.equals(code, cacheCode)) {
            log.warn("验证码不匹配或不存在: {}", mobile);
            return false; // 验证码不匹配或不存在时返回false
        }
        // 校验通过，删除验证码
        redisUtil.remove(RedisConstants.Captcha.MOBILE_CODE + mobile);

        // 校验手机号是否已注册
        long count = this.count(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getMobile, mobile)
                .or()
                .eq(SysUser::getUsername, mobile)
        );
        Assert.isTrue(count == 0, "手机号已注册");

        SysUser entity = new SysUser();
        entity.setUsername(mobile);
        entity.setMobile(mobile);
        entity.setStatus(GlobalConstants.STATUS_YES);

        // 设置默认加密密码
        String defaultEncryptPwd = passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD);
        entity.setPassword(defaultEncryptPwd);

        // 新增用户，并直接返回结果
        return this.save(entity);
    }

    /**
     * 获取用户个人中心信息
     *
     * @return {@link UserProfileVO}
     */
    @Override
    public UserProfileVO getUserProfile() {
        Long userId = RpcUtil.getUserId();
        // 获取用户个人中心信息
        UserProfileBO userProfileBO = this.baseMapper.getUserProfile(userId);
        return userConverter.toProfileVo(userProfileBO);
    }

    /**
     * 修改个人中心用户信息
     *
     * @param formData 表单数据
     * @return true|false
     */
    @Override
    public boolean updateUserProfile(UserProfileForm formData) {
        Long userId = RpcUtil.getUserId();
        SysUser entity = userConverter.toEntity(formData);
        entity.setId(userId);
        return this.updateById(entity);
    }

    /**
     * 修改用户密码
     *
     * @param userId 用户ID
     * @param data   密码修改表单数据
     * @return true|false
     */
    @Override
    public boolean changePassword(Long userId, PasswordUpdateForm data) {

        SysUser user = this.getById(userId);
        if (user == null) {
            throw new ProviderException("用户不存在");
        }

        String oldPassword = data.getOldPassword();

        // 校验原密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ProviderException("原密码错误");
        }
        // 新旧密码不能相同
        if (passwordEncoder.matches(data.getNewPassword(), user.getPassword())) {
            throw new ProviderException("新密码不能与原密码相同");
        }

        String newPassword = data.getNewPassword();
        boolean result = this.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getPassword, passwordEncoder.encode(newPassword))
        );

        if (result) {
            // 加入黑名单，重新登录
            invalidateToke();
        }
        return result;
    }

    /**
     * 重置密码
     *
     * @param userId   用户ID
     * @param password 密码重置表单数据
     * @return true|false
     */
    @Override
    public boolean resetPassword(Long userId, String password) {
        return this.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getPassword, passwordEncoder.encode(password))
        );
    }

    /**
     * 发送注册短信验证码
     *
     * @param mobile 手机号
     * @return true|false 是否发送成功
     */
    @Override
    public boolean sendMobileCode(String mobile) {
        // 获取短信模板代码
        String templateCode = aliyunSmsProperties.getTemplateCodes().get("register");

        // 生成随机4位数验证码
        String code = RandomUtil.randomNumbers(4);

        // 短信模板: 您的验证码：${code}，该验证码5分钟内有效，请勿泄漏于他人。
        // 其中 ${code} 是模板参数，使用时需要替换为实际值。
        String templateParams = JSONUtil.toJsonStr(Collections.singletonMap("code", code));

        boolean result = smsService.sendSms(mobile, templateCode, templateParams);
        if (result) {
            // 将验证码存入redis，有效期5分钟
            redisUtil.set(RedisConstants.Captcha.MOBILE_CODE + mobile, code, 5L, TimeUnit.MINUTES);

            // TODO 考虑记录每次发送短信的详情，如发送时间、手机号和短信内容等，以便后续审核或分析短信发送效果。
        }
        return result;
    }

    /**
     * 绑定或更换手机号
     *
     * @param form 表单数据
     * @return true|false
     */
    @Override
    public boolean bindOrChangeMobile(MobileUpdateForm form) {

        Long currentUserId = RpcUtil.getUserId();
        SysUser currentUser = this.getById(currentUserId);

        if (currentUser == null) {
            throw new ProviderException("用户不存在");
        }

        // 校验验证码
        String inputVerifyCode = form.getCode();
        String mobile = form.getMobile();

        String cacheKey = RedisConstants.Captcha.MOBILE_CODE + mobile;

        String cachedVerifyCode = redisUtil.get(cacheKey);

        if (StrUtil.isBlank(cachedVerifyCode)) {
            throw new ProviderException("验证码已过期");
        }
        if (!inputVerifyCode.equals(cachedVerifyCode)) {
            throw new ProviderException("验证码错误");
        }
        // 验证完成删除验证码
        redisUtil.remove(cacheKey);

        // 更新手机号码
        return this.update(
                new LambdaUpdateWrapper<SysUser>()
                        .eq(SysUser::getId, currentUserId)
                        .set(SysUser::getMobile, mobile)
        );
    }

    /**
     * 发送邮箱验证码（绑定或更换邮箱）
     *
     * @param email 邮箱
     */
    @Override
    public void sendEmailCode(String email) {

        // String code = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        // TODO 为了方便测试，验证码固定为 1234，实际开发中在配置了邮箱服务后，可以使用上面的随机验证码
        String code = "1234";

        mailService.sendMail(email, "邮箱验证码", "您的验证码为：" + code + "，请在5分钟内使用");
        // 缓存验证码，5分钟有效，用于更换邮箱校验
        String redisCacheKey = RedisConstants.Captcha.EMAIL_CODE + email;
        redisUtil.set(redisCacheKey, code, 5L, TimeUnit.MINUTES);
    }

    /**
     * 修改当前用户邮箱
     *
     * @param form 表单数据
     * @return true|false
     */
    @Override
    public boolean bindOrChangeEmail(EmailUpdateForm form) {

        Long currentUserId = RpcUtil.getUserId();

        SysUser currentUser = this.getById(currentUserId);
        if (currentUser == null) {
            throw new ProviderException("用户不存在");
        }

        // 获取前端输入的验证码
        String inputVerifyCode = form.getCode();

        // 获取缓存的验证码
        String email = form.getEmail();
        String redisCacheKey = RedisConstants.Captcha.EMAIL_CODE + email;
        String cachedVerifyCode = redisUtil.get(redisCacheKey);

        if (StrUtil.isBlank(cachedVerifyCode)) {
            throw new ProviderException("验证码已过期");
        }

        if (!inputVerifyCode.equals(cachedVerifyCode)) {
            throw new ProviderException("验证码错误");
        }
        // 验证完成删除验证码
        redisUtil.remove(redisCacheKey);

        // 更新邮箱地址
        return this.update(
                new LambdaUpdateWrapper<SysUser>()
                        .eq(SysUser::getId, currentUserId)
                        .set(SysUser::getEmail, email)
        );
    }

    /**
     * 获取用户选项列表
     *
     * @return {@link List<Option<String>>} 用户选项列表
     */
    @Override
    public List<Option<String>> listUserOptions() {
        List<SysUser> list = this.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, 1)
        );
        return userConverter.toOptions(list);
    }


    /**
     * 根据用户名获取认证信息
     *
     * @param username 用户名
     * @return 用户认证信息 {@link UserAuthInfo}
     */
    @Override
    public UserAuthInfo getUserAuthInfo(String username) {
        UserAuthInfo userAuthInfo = this.baseMapper.getUserAuthInfo(username);
        if (userAuthInfo != null) {
            Set<String> roles = userAuthInfo.getRoles();
            if (CollectionUtil.isNotEmpty(roles)) {
                // 获取最大范围的数据权限(目前设定DataScope越小，拥有的数据权限范围越大，所以获取得到角色列表中最小的DataScope)
                Integer dataScope = roleService.getMaximumDataScope(roles);
                userAuthInfo.setDataScope(dataScope);
            }
        }
        return userAuthInfo;
    }

    @Override
    public boolean logout() {
        invalidateToke();
        return true;
    }

    private void invalidateToke() {
        String jti = RpcUtil.getJti();
        if (jti == null) {
            return;
        }
        Optional<Instant> expireTimeOpt = Optional.ofNullable(RpcUtil.getExp()); // 使用Optional处理可能的null值

        long currentTimeInSeconds = System.currentTimeMillis() / 1000; // 当前时间（单位：秒）

        expireTimeOpt.ifPresent(expireTime -> {
            if (expireTime.getEpochSecond() > currentTimeInSeconds) {
                // token未过期，添加至缓存作为黑名单，缓存时间为token剩余的有效时间
                long remainingTimeInSeconds = expireTime.getEpochSecond() - currentTimeInSeconds;
                redisUtil.set(RedisConstants.Auth.BLACKLIST_TOKEN + jti, "", remainingTimeInSeconds, TimeUnit.SECONDS);
            }
        });

        if (expireTimeOpt.isEmpty()) {
            // token 永不过期则永久加入黑名单
            redisUtil.set(RedisConstants.Auth.BLACKLIST_TOKEN + jti, "");
        }
    }
}
