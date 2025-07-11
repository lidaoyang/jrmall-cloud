package com.jrmall.mall.ums.convert;

import com.jrmall.mall.ums.model.dto.MemberAuthDTO;
import com.jrmall.mall.ums.model.dto.MemberInfoDTO;
import com.jrmall.mall.ums.model.dto.MemberRegisterDto;
import com.jrmall.mall.ums.model.entity.UmsMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *  会员对象转换器
 *
 * @author haoxr
 * @since 2022/6/11
 */
@Mapper(componentModel = "spring")
public interface MemberConvert {
    @Mappings({
            @Mapping(target = "username", source = "openid")
    })
    MemberAuthDTO entity2OpenidAuthDTO(UmsMember entity);

    @Mappings({
            @Mapping(target = "username", source = "mobile")
    })
    MemberAuthDTO entity2MobileAuthDTO(UmsMember entity);

    MemberInfoDTO entity2MemberInfoDTO(UmsMember entity);

    UmsMember dto2Entity(MemberRegisterDto memberRegisterDTO);
}
