package com.jrmall.cloud.system.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


/**
 * 字典分页VO
 *
 * @author Ray
 * @since 0.0.1
 */
@Schema(description = "字典分页对象")
@Getter
@Setter
public class DictPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典ID")
    private Long id;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典编码")
    private String dictCode;

    @Schema(description = "字典状态（1-启用，0-禁用）")
    private Integer status;

}
