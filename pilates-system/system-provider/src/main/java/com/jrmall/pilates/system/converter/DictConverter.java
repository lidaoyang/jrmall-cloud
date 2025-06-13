package com.jrmall.pilates.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.system.model.entity.SysDict;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import org.mapstruct.Mapper;

/**
 * 字典 对象转换器
 *
 * @author Ray Hao
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> toPageVo(Page<SysDict> page);

    DictForm toForm(SysDict entity);

    SysDict toEntity(DictForm entity);
}
