package com.jrmall.pilates.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.pilates.system.api.UserApi;
import com.jrmall.pilates.system.model.form.UserForm;
import com.jrmall.pilates.system.model.form.UserRegisterForm;
import com.jrmall.pilates.system.model.query.UserPageQuery;
import com.jrmall.pilates.system.model.vo.*;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "修改用户密码")
    @PatchMapping(value = "/{userId}/password")
    @PreAuthorize("@ss.hasPerm('sys:user:reset_pwd')")
    public Result<Boolean> updatePassword(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestParam String password
    ) {
        boolean result = userApi.updatePassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "修改用户状态")
    @PatchMapping(value = "/{userId}/status")
    @PreAuthorize("@ss.hasPerm('sys:user:upstatus')")
    public Result<Boolean> updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "用户状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = userApi.updateStatus(userId, status);
        return Result.judge(result);
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/me")
    public Result<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfoVO = userApi.getCurrentUserInfo();
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
        String fileName = "用户列表.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        List<UserExportVO> exportUserList = userApi.listExportUsers(queryParams);
        EasyExcel.write(response.getOutputStream(), UserExportVO.class).sheet("用户列表")
                .doWrite(exportUserList);
    }

    @Operation(summary = "注册用户")
    @PostMapping("/register")
    public Result<Boolean> registerUser(
            @RequestBody @Valid UserRegisterForm userRegisterForm
    ) {
        boolean result = userApi.registerUser(userRegisterForm);
        return Result.judge(result);
    }

    @Operation(summary = "发送注册短信验证码")
    @PostMapping("/register/sms_code")
    public Result<Boolean> sendRegistrationSmsCode(
            @Parameter(description = "手机号") @RequestParam String mobile
    ) {
        boolean result = userApi.sendRegistrationSmsCode(mobile);
        return Result.judge(result);
    }

    @Operation(summary = "获取用户个人中心信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getUserProfile() {
        UserProfileVO userProfile = userApi.getUserProfile();
        return Result.success(userProfile);
    }


}
