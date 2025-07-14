package com.jrmall.cloud.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.cloud.common.base.Option;
import com.jrmall.cloud.common.excel.util.EasyExcelUtil;
import com.jrmall.cloud.common.result.PageResult;
import com.jrmall.cloud.common.result.Result;
import com.jrmall.cloud.common.security.util.SecurityUtils;
import com.jrmall.cloud.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.cloud.system.api.UserApi;
import com.jrmall.cloud.system.model.form.*;
import com.jrmall.cloud.system.model.vo.CurrentUserVO;
import com.jrmall.cloud.system.model.vo.UserExportVO;
import com.jrmall.cloud.system.model.vo.UserPageVO;
import com.jrmall.cloud.system.model.vo.UserProfileVO;
import com.jrmall.cloud.system.model.query.UserPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 用户控制器
 *
 * @author haoxr
 * @since 2022/10/16
 */
@Tag(name = "01.用户接口")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class SysUserController {

    @DubboReference
    private UserApi userApi;

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('sys:user:list')")
    public PageResult<UserPageVO> getUserPage(
            @ParameterObject UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = userApi.getUserPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @PreventDuplicateResubmit
    public Result<Boolean> saveUser(
            @RequestBody @Valid UserForm userForm
    ) {
        boolean result = userApi.saveUser(userForm);
        return Result.judge(result);
    }

    @Operation(summary = "用户表单数据")
    @GetMapping("/{userId}/form")
    public Result<UserForm> getUserForm(
            @Parameter(description = "用户ID") @PathVariable Long userId
    ) {
        UserForm formData = userApi.getUserFormData(userId);
        return Result.success(formData);
    }

    @Operation(summary = "修改用户")
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result<Boolean> updateUser(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody @Validated UserForm userForm) {
        boolean result = userApi.updateUser(userId, userForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result<Boolean> deleteUsers(
            @Parameter(description = "用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = userApi.deleteUsers(ids);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户状态")
    @PatchMapping(value = "/{userId}/status")
    @PreAuthorize("@ss.hasPerm('sys:user:upstatus')")
    public Result<Boolean> updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "用户状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = userApi.updateUserStatus(userId, status);
        return Result.judge(result);
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/me")
    public Result<CurrentUserVO> getCurrentUserInfo() {
        CurrentUserVO userInfoVO = userApi.getCurrentUserInfo();
        return Result.success(userInfoVO);
    }

    @Operation(summary = "用户导入模板下载")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        String fileClassPath = "excel-templates" + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @Operation(summary = "导出用户")
    @GetMapping("/export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response) throws IOException {
        List<UserExportVO> exportUserList = userApi.listExportUsers(queryParams);
        EasyExcelUtil.download(response, exportUserList, "用户列表");
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public Result<Boolean> registerUser(
            @RequestBody @Valid UserRegisterForm userRegisterForm
    ) {
        boolean result = userApi.registerUser(userRegisterForm);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户个人中心信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getUserProfile() {
        UserProfileVO userProfile = userApi.getUserProfile();
        return Result.success(userProfile);
    }

    @Operation(summary = "个人中心修改用户信息")
    @PutMapping("/profile")
    public Result<?> updateUserProfile(@RequestBody UserProfileForm formData) {
        boolean result = userApi.updateUserProfile(formData);
        return Result.judge(result);
    }

    @Operation(summary = "重置用户密码")
    @PutMapping(value = "/{userId}/password/reset")
    @PreAuthorize("@ss.hasPerm('sys:user:reset-password')")
    public Result<?> resetPassword(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestParam String password) {
        boolean result = userApi.resetPassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "修改密码")
    @PutMapping(value = "/password")
    public Result<?> changePassword(
            @RequestBody PasswordUpdateForm data
    ) {
        Long currUserId = SecurityUtils.getUserId();
        boolean result = userApi.changePassword(currUserId, data);
        return Result.judge(result);
    }

    @Operation(summary = "发送短信验证码", description = "注册,绑定,更换手机号发送短信验证码")
    @PostMapping(value = "/mobile/code")
    public Result<?> sendMobileCode(
            @Parameter(description = "手机号码", required = true) @RequestParam String mobile
    ) {
        boolean result = userApi.sendMobileCode(mobile);
        return Result.judge(result);
    }

    @Operation(summary = "绑定或更换手机号")
    @PutMapping(value = "/mobile")
    public Result<?> bindOrChangeMobile(
            @RequestBody @Validated MobileUpdateForm data
    ) {
        boolean result = userApi.bindOrChangeMobile(data);
        return Result.judge(result);
    }

    @Operation(summary = "发送邮箱验证码（绑定或更换邮箱）")
    @PostMapping(value = "/email/code")
    public Result<Void> sendEmailCode(
            @Parameter(description = "邮箱地址", required = true) @RequestParam String email
    ) {
        userApi.sendEmailCode(email);
        return Result.success();
    }

    @Operation(summary = "绑定或更换邮箱")
    @PutMapping(value = "/email")
    public Result<?> bindOrChangeEmail(
            @RequestBody @Validated EmailUpdateForm data
    ) {
        boolean result = userApi.bindOrChangeEmail(data);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户下拉选项")
    @GetMapping("/options")
    public Result<List<Option<String>>> listUserOptions() {
        List<Option<String>> list = userApi.listUserOptions();
        return Result.success(list);
    }

}
