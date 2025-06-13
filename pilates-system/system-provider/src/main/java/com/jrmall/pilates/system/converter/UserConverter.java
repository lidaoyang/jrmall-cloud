package com.jrmall.pilates.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.model.bo.UserBO;
import com.jrmall.pilates.system.model.bo.UserFormBO;
import com.jrmall.pilates.system.model.bo.UserProfileBO;
import com.jrmall.pilates.system.model.entity.SysUser;
import com.jrmall.pilates.system.model.form.UserForm;
import com.jrmall.pilates.system.model.form.UserProfileForm;
import com.jrmall.pilates.system.model.vo.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 用户对象转换器
 *
 * @author haoxr
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserPageVO toPageVo(UserBO bo);

    Page<UserPageVO> toPageVo(Page<UserBO> bo);

    UserForm toForm(SysUser entity);

    UserForm bo2Form(UserFormBO bo);

    @InheritInverseConfiguration(name = "toForm")
    SysUser toEntity(UserForm entity);

    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    CurrentUserVO toCurrentUserVo(SysUser entity);


    UserProfileVO toProfileVo(UserProfileBO bo);

    SysUser toEntity(UserProfileForm formData);

    @Mappings({
            @Mapping(target = "label", source = "nickname"),
            @Mapping(target = "value", source = "id")
    })
    Option<String> toOption(SysUser entity);

    List<Option<String>> toOptions(List<SysUser> list);
}
