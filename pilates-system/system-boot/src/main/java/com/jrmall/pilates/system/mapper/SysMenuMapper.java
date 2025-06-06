package com.jrmall.pilates.system.mapper;

/**
 * 菜单持久接口层
 *
 * @author haoxr
 * @since 2022/1/24
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jrmall.pilates.system.model.bo.RouteBO;
import com.jrmall.pilates.system.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("""
                   SELECT
                       t1.id, t1.name, t1.parent_id, t1.path, t1.component, t1.icon, t1.sort, t1.visible, t1.redirect, t1.type, t3.code as roles, t1.always_show, t1.keep_alive
                    FROM
                        sys_menu t1
                             LEFT JOIN sys_role_menu t2 ON t1.id = t2.menu_id
                             LEFT JOIN sys_role t3 ON t2.role_id = t3.id
                    WHERE
                        t1.type != 4
                    ORDER BY t1.sort asc
            """)
    List<RouteBO> listRoutes();
}
