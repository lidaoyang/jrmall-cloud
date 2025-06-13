package com.jrmall.pilates.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * 键值对
 *
 * @author haoxr
 * @since 2024/5/25
 */
@Schema(description = "键值对")
@Data
@NoArgsConstructor
public class KeyValue implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Schema(description = "选项的值")
    private String key;

    @Schema(description = "选项的标签")
    private String value;

}