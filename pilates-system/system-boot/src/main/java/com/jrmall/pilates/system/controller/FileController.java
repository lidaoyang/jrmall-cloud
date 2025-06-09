package com.jrmall.pilates.system.controller;

import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.system.model.vo.FileInfoVO;
import com.jrmall.pilates.system.service.OssService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "06.文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final OssService ossService;

    @PostMapping
    @Operation(summary = "文件上传")
    public Result<FileInfoVO> uploadFile(
            @Parameter(name = "表单文件对象") @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfoVO fileInfo = ossService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping
    @Operation(summary = "文件删除")
    public Result<Boolean> deleteFile(
            @Parameter(name = "文件路径") @RequestParam String filePath
    ) {
        boolean result = ossService.deleteFile(filePath);
        return Result.judge(result);
    }

}
