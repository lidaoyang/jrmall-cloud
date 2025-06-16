package com.jrmall.pilates.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.system.model.entity.SysConfig;
import com.jrmall.pilates.system.model.form.ConfigForm;
import com.jrmall.pilates.system.model.vo.ConfigVO;
import org.mapstruct.Mapper;

/**
 * 系统配置对象转换器
 *
 * @author Theo
 * @since 2024-7-29 11:42:49
 */
@Mapper(componentModel = "spring")
public interface ConfigConverter {

    Page<ConfigVO> toPageVo(Page<SysConfig> page);

    SysConfig toEntity(ConfigForm configForm);

    ConfigForm toForm(SysConfig entity);
}
