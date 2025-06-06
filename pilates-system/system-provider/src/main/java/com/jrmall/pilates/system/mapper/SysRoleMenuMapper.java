package com.jrmall.pilates.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jrmall.pilates.system.model.bo.RolePermsBO;
import com.jrmall.pilates.system.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色菜单持久层
 *
 * @author haoxr
 * @since 2022/6/4
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 获取角色拥有的菜单ID集合
     *
     * @param roleId
     * @return
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     */
    List<RolePermsBO> getRolePermsList(String roleCode);
}
