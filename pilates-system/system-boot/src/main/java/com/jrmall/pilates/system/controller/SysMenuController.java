package com.jrmall.pilates.system.controller;

import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.pilates.system.api.MenuApi;
import com.jrmall.pilates.system.model.form.MenuForm;
import com.jrmall.pilates.system.model.query.MenuQuery;
import com.jrmall.pilates.system.model.vo.MenuVO;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.model.vo.RouteVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单控制器
 *
 * @author haoxr
 * @since 2020/11/06
 */
@Tag(name = "03.菜单接口")
@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    @DubboReference
    private MenuApi menuApi;

    @Operation(summary = "菜单列表")
    @GetMapping
    public Result<List<MenuVO>> listMenus(@ParameterObject MenuQuery queryParams) {
        List<MenuVO> menuList = menuApi.listMenus(queryParams);
        return Result.success(menuList);
    }

    @Operation(summary = "菜单下拉列表")
    @GetMapping("/options")
    public Result<List<Option<Long>>> listMenuOptions() {
        List<Option<Long>> menus = menuApi.listMenuOptions();
        return Result.success(menus);
    }

    @Operation(summary = "路由列表")
    @GetMapping("/routes")
    public Result<List<RouteVO>> listRoutes() {
        List<RouteVO> routeList = menuApi.listRoutes();
        return Result.success(routeList);
    }

    @Operation(summary = "菜单表单数据")
    @GetMapping("/{id}/form")
    public Result<MenuForm> getMenuForm(
            @Parameter(description = "菜单ID") @PathVariable Long id
    ) {
        MenuForm menu = menuApi.getMenuForm(id);
        return Result.success(menu);
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:menu:add')")
    @PreventDuplicateResubmit
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result<Boolean> addMenu(@RequestBody MenuForm menuForm) {
        boolean result = menuApi.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:edit')")
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result<Boolean> updateMenu(
            @RequestBody MenuForm menuForm
    ) {
        boolean result = menuApi.saveMenu(menuForm);
        return Result.judge(result);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:menu:delete')")
    @CacheEvict(cacheNames = "menu", key = "'routes'")
    public Result<Boolean> deleteMenu(
            @Parameter(description = "菜单ID，多个以英文(,)分割") @PathVariable("id") Long id
    ) {
        boolean result = menuApi.deleteMenu(id);
        return Result.judge(result);
    }

    @Operation(summary = "修改菜单显示状态")
    @PatchMapping("/{menuId}")
    public Result<Boolean> updateMenuVisible(
            @Parameter(description = "菜单ID") @PathVariable Long menuId,
            @Parameter(description = "显示状态(1:显示;0:隐藏)") Integer visible

    ) {
        boolean result = menuApi.updateMenuVisible(menuId, visible);
        return Result.judge(result);
    }


}

