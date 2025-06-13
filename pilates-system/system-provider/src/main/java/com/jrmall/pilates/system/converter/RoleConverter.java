package com.jrmall.pilates.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.model.entity.SysRole;
import com.jrmall.pilates.system.model.form.RoleForm;
import com.jrmall.pilates.system.model.vo.RolePageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 角色对象转换器
 *
 * @author haoxr
 * @since 2022/5/29
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {


    Page<RolePageVO> toPageVo(Page<SysRole> page);

    @Mappings({
            @Mapping(target = "value", source = "id"),
            @Mapping(target = "label", source = "name")
    })
    Option<Long> toOption(SysRole role);

    List<Option<Long>> toOptions(List<SysRole> roles);

    SysRole toEntity(RoleForm roleForm);

    RoleForm toForm(SysRole entity);
}