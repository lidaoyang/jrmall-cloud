package com.jrmall.cloud.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典项键值对象
 *
 * @author Ray.Hao
 * @since 0.0.1
 */
@Schema(description = "字典项键值对象")
@Getter
@Setter
public class DictItemOptionVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典项值")
    private String value;

    @Schema(description = "字典项标签")
    private String label;

    @Schema(description = "标签类型")
    private String tagType;

}
