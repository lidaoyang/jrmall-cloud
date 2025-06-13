package com.jrmall.pilates.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改手机表单
 *
 * @author Ray.Hao
 * @since 2024/8/19
 */
@Schema(description = "修改手机表单")
@Data
public class MobileUpdateForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
