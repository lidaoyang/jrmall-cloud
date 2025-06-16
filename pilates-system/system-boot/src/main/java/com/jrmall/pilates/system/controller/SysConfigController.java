package com.jrmall.pilates.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.system.api.ConfigApi;
import com.jrmall.pilates.system.model.form.ConfigForm;
import com.jrmall.pilates.system.model.query.ConfigPageQuery;
import com.jrmall.pilates.system.model.vo.ConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置前端控制层
 *
 * @author Theo
 * @since 2024-07-30 11:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "08.系统配置")
@RequestMapping("/api/v1/config")
public class SysConfigController {

    @DubboReference
    private ConfigApi configApi;

    @Operation(summary = "系统配置分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('sys:config:query')")
    public PageResult<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = configApi.page(configPageQuery);
        return PageResult.success(result);
    }

    @Operation(summary = "新增系统配置")
    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('sys:config:add')")
    public Result<?> save(@RequestBody @Valid ConfigForm configForm) {
        return Result.judge(configApi.save(configForm));
    }

    @Operation(summary = "获取系统配置表单数据")
    @GetMapping("/{id}/form")
    public Result<ConfigForm> getConfigForm(
            @Parameter(description = "系统配置ID") @PathVariable Long id
    ) {
        ConfigForm formData = configApi.getConfigFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "刷新系统配置缓存")
    @PutMapping("/refresh")
    @PreAuthorize("@ss.hasPerm('sys:config:refresh')")
    public Result<ConfigForm> refreshCache() {
        return Result.judge(configApi.refreshCache());
    }

    @Operation(summary = "修改系统配置")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:config:update')")
    public Result<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return Result.judge(configApi.edit(id, configForm));
    }

    @Operation(summary = "删除系统配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:config:delete')")
    public Result<?> delete(@PathVariable Long id) {
        return Result.judge(configApi.delete(id));
    }

}
