package com.jrmall.pilates.system.converter;

import com.jrmall.pilates.system.model.entity.SysMenu;
import com.jrmall.pilates.system.model.form.MenuForm;
import com.jrmall.pilates.system.model.vo.MenuVO;
import org.mapstruct.Mapper;

/**
 * 菜单对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    MenuVO entity2Vo(SysMenu entity);


    MenuForm entity2Form(SysMenu entity);

    SysMenu form2Entity(MenuForm menuForm);

}