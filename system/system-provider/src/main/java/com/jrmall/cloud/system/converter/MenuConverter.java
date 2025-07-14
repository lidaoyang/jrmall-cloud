package com.jrmall.cloud.system.converter;

import com.jrmall.cloud.system.model.entity.SysMenu;
import com.jrmall.cloud.system.model.form.MenuForm;
import com.jrmall.cloud.system.model.vo.MenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 菜单对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO entity2Vo(SysMenu entity);

    @Mapping(target = "params", ignore = true)
    MenuForm toForm(SysMenu entity);

    @Mapping(target = "params", ignore = true)
    SysMenu form2Entity(MenuForm menuForm);

}