package com.jrmall.pilates.system.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典表单对象
 *
 * @author Ray Hao
 * @since 2.9.0
 */
@Schema(description = "字典")
@Data
public class DictForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典ID",example = "1")
    private Long id;

    @Schema(description = "字典名称",example = "性别")
    private String name;

    @Schema(description = "字典编码", example ="gender")
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "字典状态（1-启用，0-禁用）", example = "1")
    @Min(value = 0, message = "字典状态不能小于0")
    @Max(value = 1, message = "字典状态不能大于1")
    private Integer status;

}
