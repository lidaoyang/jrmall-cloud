package com.jrmall.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jrmall.mall.ums.model.entity.UmsAddress;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UmsAddressMapper extends BaseMapper<UmsAddress> {

    @Select("""
             SELECT * from ums_address where member_id =#{userId} 
           """)
    List<UmsAddress> listByUserId(Long userId);

}
