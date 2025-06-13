package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.RoleApi;
import com.jrmall.pilates.system.model.form.RoleForm;
import com.jrmall.pilates.system.model.query.RolePageQuery;
import com.jrmall.pilates.system.model.vo.RolePageVO;
import com.jrmall.pilates.system.service.SysRoleService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 角色接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 17:17
 */
@DubboService
public class RoleApiImpl implements RoleApi {

    @Resource
    private SysRoleService roleService;

    @Override
    public boolean assignMenusToRole(Long roleId, List<Long> menuIds) {
        roleService.assignMenusToRole(roleId, menuIds);
        return true;
    }

    @Override
    public Page<RolePageVO> getRolePage(RolePageQuery queryParams) {
        return roleService.getRolePage(queryParams);
    }

    @Override
    public List<Option<Long>> listRoleOptions() {
        return roleService.listRoleOptions();
    }

    @Override
    public boolean saveRole(RoleForm roleForm) {
        return roleService.saveRole(roleForm);
    }

    @Override
    public RoleForm getRoleForm(Long roleId) {
        return roleService.getRoleForm(roleId);
    }

    @Override
    public boolean updateRoleStatus(Long roleId, Integer status) {
        return roleService.updateRoleStatus(roleId, status);
    }

    @Override
    public boolean deleteRoles(String ids) {
        roleService.deleteRoles(ids);
        return true;
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return roleService.getRoleMenuIds(roleId);
    }
}
