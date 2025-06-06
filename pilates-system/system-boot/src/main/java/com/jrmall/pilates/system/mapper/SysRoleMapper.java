package com.jrmall.pilates.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jrmall.pilates.system.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


    /**
     * 获取最大范围的数据权限
     *
     * @param roles
     * @return
     */
    @Select("SELECT MIN(data_scope) FROM sys_role WHERE code IN (${roles})")
    Integer getMaxDataRangeDataScope(String roles);
}
