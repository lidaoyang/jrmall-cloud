package com.jrmall.pilates.system.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "字典类型")
@Data
public class DictTypeForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description="字典类型ID")
    private Long id;

    @Schema(description="类型名称")
    private String name;

    @Schema(description="类型编码")
    private String code;

    @Schema(description="类型状态(1:启用;0:禁用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

}
