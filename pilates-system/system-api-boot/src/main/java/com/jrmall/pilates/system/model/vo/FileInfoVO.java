package com.jrmall.pilates.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "文件对象")
@Data
public class FileInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件URL")
    private String url;

}
