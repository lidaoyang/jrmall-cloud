package com.jrmall.pilates.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.model.entity.SysMenu;
import com.jrmall.pilates.system.model.form.MenuForm;
import com.jrmall.pilates.system.model.query.MenuQuery;
import com.jrmall.pilates.system.model.vo.MenuVO;
import com.jrmall.pilates.system.model.vo.RouteVO;

import java.util.List;

/**
 * 菜单业务接口
 * 
 * @author haoxr
 * @since 2020/11/06
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取菜单表格列表
     *
     * @return
     */
    List<MenuVO> listMenus(MenuQuery queryParams);


    /**
     * 获取菜单下拉列表
     *
     * @return
     */
    List<Option<Long>> listMenuOptions();

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    boolean saveMenu(MenuForm menu);

    /**
     * 获取路由列表
     *
     * @return
     */
    List<RouteVO> listRoutes();

    /**
     * 修改菜单显示状态
     * 
     * @param menuId 菜单ID
     * @param visible 是否显示(1->显示；2->隐藏)
     * @return
     */
    boolean updateMenuVisible(Long menuId, Integer visible);

    /**
     * 获取菜单表单数据
     *
     * @param id 菜单ID
     * @return
     */
    MenuForm getMenuForm(Long id);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    boolean deleteMenu(Long id);
}
