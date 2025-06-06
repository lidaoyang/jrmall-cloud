package com.jrmall.pilates.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jrmall.pilates.system.model.bo.RolePermsBO;
import com.jrmall.pilates.system.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

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
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    @Select("""
            SELECT rm.menu_id
            FROM sys_role_menu rm
            INNER JOIN sys_menu m ON rm.menu_id = m.id
            WHERE rm.role_id = #{roleId}
            """)
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 获取权限和拥有权限的角色列表
     */
    List<RolePermsBO> getRolePermsList(String roleCode);
}
