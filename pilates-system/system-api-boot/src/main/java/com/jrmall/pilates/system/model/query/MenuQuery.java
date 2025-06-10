package com.jrmall.pilates.system.model.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单查询对象
 *
 * @author haoxr
 * @since 2022/10/28
 */
@Schema(description = "部门分页查询对象")
@Data
public class MenuQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "关键字(菜单名称)")
    private String keywords;

    @Schema(description = "状态(1->显示；0->隐藏)")
    private Integer status;

}
