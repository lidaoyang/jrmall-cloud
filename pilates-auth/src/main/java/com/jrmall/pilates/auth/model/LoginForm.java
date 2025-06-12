package com.jrmall.pilates.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录表单对象
 *
 * @author haoxr
 * @since 2022/4/12 11:04
 */
@Schema(description = "登录表单对象")
@Data
public class LoginForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String password;

    @Schema(description = "验证码缓存Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码缓存Id不能为空")
    private String captchaId;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String captchaCode;


}
