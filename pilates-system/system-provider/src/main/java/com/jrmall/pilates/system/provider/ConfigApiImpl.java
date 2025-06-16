package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.pilates.system.api.ConfigApi;
import com.jrmall.pilates.system.model.form.ConfigForm;
import com.jrmall.pilates.system.model.query.ConfigPageQuery;
import com.jrmall.pilates.system.model.vo.ConfigVO;
import com.jrmall.pilates.system.service.SysConfigService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 系统配置接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/16 15:35
 */
@DubboService
public class ConfigApiImpl implements ConfigApi {

    @Resource
    private SysConfigService configService;

    @Override
    public IPage<ConfigVO> page(ConfigPageQuery sysConfigPageQuery) {
        return configService.page(sysConfigPageQuery);
    }

    @Override
    public boolean save(ConfigForm sysConfigForm) {
        return configService.save(sysConfigForm);
    }

    @Override
    public ConfigForm getConfigFormData(Long id) {
        return configService.getConfigFormData(id);
    }

    @Override
    public boolean edit(Long id, ConfigForm sysConfigForm) {
        return configService.edit(id, sysConfigForm);
    }

    @Override
    public boolean delete(Long ids) {
        return configService.delete(ids);
    }

    @Override
    public boolean refreshCache() {
        return configService.refreshCache();
    }
}
