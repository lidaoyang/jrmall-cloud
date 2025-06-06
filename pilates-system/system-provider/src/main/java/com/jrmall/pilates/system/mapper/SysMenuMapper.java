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

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<RouteBO> listRoutes();
}
