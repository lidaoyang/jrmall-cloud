package com.jrmall.cloud.auth.controller;

import com.jrmall.cloud.auth.model.CaptchaResult;
import com.jrmall.cloud.auth.model.LoginForm;
import com.jrmall.cloud.auth.service.AuthService;
import com.jrmall.cloud.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * <p>
 * 获取验证码、退出登录等接口
 * 注：登录接口不在此控制器，在过滤器OAuth2TokenEndpointFilter拦截端点(/oauth2/token)处理
 *
 * @author haoxr
 * @since 3.1.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public void login(HttpServletRequest request,
                       LoginForm loginForm,
                      HttpServletResponse response) {
        authService.login(request,loginForm, response);
    }

    @Operation(summary = "登出")
    @DeleteMapping("/logout")
    public Result<Boolean> logout() {
        boolean result = authService.logout();
        return Result.judge(result);
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResult> getCaptcha() {
        CaptchaResult captchaResult = authService.getCaptcha();
        return Result.success(captchaResult);
    }

    @Operation(summary = "发送手机短信验证码")
    @PostMapping("/sms_code")
    public Result<Boolean> sendLoginSmsCode(
            @Parameter(description = "手机号") @RequestParam String mobile
    ) {
        boolean result = authService.sendLoginSmsCode(mobile);
        return Result.judge(result);
    }

}
