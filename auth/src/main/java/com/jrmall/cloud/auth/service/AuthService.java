package com.jrmall.cloud.auth.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.jrmall.cloud.auth.oauth2.extension.captcha.CaptchaAuthenticationToken;
import com.jrmall.cloud.auth.oauth2.extension.captcha.CaptchaParameterNames;
import com.jrmall.cloud.common.constant.RedisConstants;
import com.jrmall.cloud.common.redis.util.RedisUtil;
import com.jrmall.cloud.common.web.http.CustomRequestWrapper;
import com.jrmall.cloud.auth.model.LoginForm;
import com.jrmall.cloud.auth.property.CaptchaProperties;
import com.jrmall.cloud.auth.model.CaptchaResult;
import com.jrmall.cloud.common.sms.property.AliyunSmsProperties;
import com.jrmall.cloud.common.sms.service.SmsService;
import com.jrmall.cloud.system.api.UserAuthApi;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    @DubboReference
    private UserAuthApi userAuthApi;

    private final CaptchaProperties captchaProperties;
    private final CaptchaService captchaService;

    private final AliyunSmsProperties aliyunSmsProperties;
    private final SmsService smsService;

    private final RedisUtil redisUtil;


    public void login(HttpServletRequest request, LoginForm loginForm, HttpServletResponse response) {
        // 1. 创建自定义的RequestWrapper
        CustomRequestWrapper requestWrapper = new CustomRequestWrapper(request);

        // 2. 设置必要参数（根据OAuth2密码模式要求）
        requestWrapper.addParameter(OAuth2ParameterNames.GRANT_TYPE, CaptchaAuthenticationToken.CAPTCHA.getValue());
        requestWrapper.addParameter(OAuth2ParameterNames.USERNAME, loginForm.getUsername());
        requestWrapper.addParameter(OAuth2ParameterNames.PASSWORD, loginForm.getPassword());
        requestWrapper.addParameter(CaptchaParameterNames.CAPTCHA_ID, loginForm.getCaptchaId());
        requestWrapper.addParameter(CaptchaParameterNames.CAPTCHA_CODE, loginForm.getCaptchaCode());
        // requestWrapper.addParameter("scope", "read write");

        // 3. 设置客户端认证头（Basic Auth方式）
        String clientCredentials = "mall-admin:123456"; // 建议从配置读取
        String authHeader = "Basic " + Base64.getEncoder().encodeToString(clientCredentials.getBytes());
        requestWrapper.addHeader("Authorization", authHeader);
        try {
            // 4. 转发到OAuth2令牌端点
            RequestDispatcher dispatcher = request.getRequestDispatcher("/oauth2/token");
            dispatcher.forward(requestWrapper, response);

        } catch (IOException | ServletException e) {
            log.error("login failed: {}", e.getMessage(), e);
        }
    }

    public boolean logout() {
        return userAuthApi.logout();
    }

    /**
     * 获取图形验证码
     *
     * @return Result<CaptchaResult>
     */
    public CaptchaResult getCaptcha() {

        AbstractCaptcha captcha = captchaService.generate();

        // 验证码文本缓存至Redis，用于登录校验
        String captchaId = IdUtil.fastSimpleUUID();
        redisUtil.set(
                RedisConstants.Captcha.IMAGE_CODE + captchaId,
                captcha.getCode(),
                captchaProperties.getExpireSeconds(),
                TimeUnit.SECONDS
        );

        return CaptchaResult.builder()
                .captchaId(captchaId)
                .captchaBase64(captcha.getImageBase64Data())
                .build();
    }

    /**
     * 发送登录短信验证码
     *
     * @param mobile 手机号
     * @return true|false 是否发送成功
     */
    public boolean sendLoginSmsCode(String mobile) {
        // 获取短信模板代码
        String templateCode = aliyunSmsProperties.getTemplateCodes().get("login");

        // 生成随机4位数验证码
        String code = RandomUtil.randomNumbers(4);

        // 短信模板: 您的验证码：${code}，该验证码5分钟内有效，请勿泄漏于他人。
        // 其中 ${code} 是模板参数，使用时需要替换为实际值。
        String templateParams = JSONUtil.toJsonStr(Collections.singletonMap("code", code));

        boolean result = smsService.sendSms(mobile, templateCode, templateParams);
        if (result) {
            // 将验证码存入redis，有效期5分钟
            redisUtil.set(RedisConstants.Captcha.SMS_LOGIN_CODE + mobile, code, 5L, TimeUnit.MINUTES);

            // TODO 考虑记录每次发送短信的详情，如发送时间、手机号和短信内容等，以便后续审核或分析短信发送效果。
        }
        return result;
    }

}
