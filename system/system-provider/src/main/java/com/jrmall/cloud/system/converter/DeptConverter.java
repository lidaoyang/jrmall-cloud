package com.jrmall.cloud.system.converter;

import com.jrmall.cloud.system.model.entity.SysDept;
import com.jrmall.cloud.system.model.form.DeptForm;
import com.jrmall.cloud.system.model.vo.DeptVO;
import org.mapstruct.Mapper;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm toForm(SysDept entity);

    DeptVO toVo(SysDept entity);

    SysDept toEntity(DeptForm deptForm);

}