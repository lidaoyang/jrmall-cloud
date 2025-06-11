package com.jrmall.pilates.system.provider;

import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.MenuApi;
import com.jrmall.pilates.system.model.form.MenuForm;
import com.jrmall.pilates.system.model.query.MenuQuery;
import com.jrmall.pilates.system.model.vo.MenuVO;
import com.jrmall.pilates.system.model.vo.RouteVO;
import com.jrmall.pilates.system.service.SysMenuService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 菜单接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 17:12
 */
@DubboService
public class MenuApiImpl implements MenuApi {

    @Resource
    private SysMenuService menuService;

    @Override
    public List<MenuVO> listMenus(MenuQuery queryParams) {
        return menuService.listMenus(queryParams);
    }

    @Override
    public List<Option<Long>> listMenuOptions() {
        return menuService.listMenuOptions();
    }

    @Override
    public boolean saveMenu(MenuForm menu) {
        return menuService.saveMenu(menu);
    }

    @Override
    public List<RouteVO> listRoutes() {
        return menuService.listRoutes();
    }

    @Override
    public boolean updateMenuVisible(Long menuId, Integer visible) {
        return menuService.updateMenuVisible(menuId, visible);
    }

    @Override
    public MenuForm getMenuForm(Long id) {
        return menuService.getMenuForm(id);
    }

    @Override
    public boolean deleteMenu(Long id) {
        return menuService.deleteMenu(id);
    }
}
