package com.jrmall.pilates.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jrmall.pilates.common.mybatis.annotation.DataPermission;
import com.jrmall.pilates.system.model.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    @DataPermission(deptIdColumnName = "id")
    @Override
    List<SysDept> selectList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);
}
