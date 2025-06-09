package com.jrmall.pilates.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.RoleApi;
import com.jrmall.pilates.system.model.form.RoleForm;
import com.jrmall.pilates.system.model.query.RolePageQuery;
import com.jrmall.pilates.system.model.vo.RolePageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author haoxr
 * @since 2020/11/6
 */
@Tag(name = "02.角色接口")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class SysRoleController {

    @DubboReference
    private RoleApi roleApi;

    @Operation(summary = "角色分页列表")
    @GetMapping("/page")
    public PageResult<RolePageVO> getRolePage(
            @ParameterObject RolePageQuery queryParams
    ) {
        Page<RolePageVO> result = roleApi.getRolePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "角色下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Integer>>> listRoleOptions() {
        List<Option<Integer>> list = roleApi.listRoleOptions();
        return Result.success(list);
    }

    @Operation(summary = "新增角色")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:role:add')")
    @PreventDuplicateResubmit
    public Result<Boolean> addRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleApi.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "角色表单数据")
    @GetMapping("/{roleId}/form")
    public Result<RoleForm> getRoleForm(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        RoleForm roleForm = roleApi.getRoleForm(roleId);
        return Result.success(roleForm);
    }

    @Operation(summary = "修改角色")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:role:edit')")
    public Result<Boolean> updateRole(@Valid @RequestBody RoleForm roleForm) {
        boolean result = roleApi.saveRole(roleForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:role:delete')")
    public Result<Boolean> deleteRoles(
            @Parameter(description = "删除角色，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = roleApi.deleteRoles(ids);
        return Result.judge(result);
    }

    @Operation(summary = "修改角色状态")
    @PutMapping(value = "/{roleId}/status")
    public Result<Boolean> updateRoleStatus(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @Parameter(description = "状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        boolean result = roleApi.updateRoleStatus(roleId, status);
        return Result.judge(result);
    }

    @Operation(summary = "获取角色的菜单ID集合")
    @GetMapping("/{roleId}/menuIds")
    public Result<List<Long>> getRoleMenuIds(
            @Parameter(description = "角色ID") @PathVariable Long roleId
    ) {
        List<Long> menuIds = roleApi.getRoleMenuIds(roleId);
        return Result.success(menuIds);
    }

    @Operation(summary = "分配菜单权限给角色")
    @PutMapping("/{roleId}/menus")
    public Result<Boolean> assignMenusToRole(
            @PathVariable Long roleId,
            @RequestBody List<Long> menuIds
    ) {
        boolean result = roleApi.assignMenusToRole(roleId, menuIds);
        return Result.judge(result);
    }
}
