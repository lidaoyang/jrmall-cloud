package com.jrmall.cloud.system.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.cloud.system.model.form.ConfigForm;
import com.jrmall.cloud.system.model.query.ConfigPageQuery;
import com.jrmall.cloud.system.model.vo.ConfigVO;

/**
 * 系统配置接口
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/16 15:34
 */
public interface ConfigApi {


    /**
     * 分页查询系统配置
     *
     * @param sysConfigPageQuery 查询参数
     * @return 系统配置分页列表
     */
    IPage<ConfigVO> page(ConfigPageQuery sysConfigPageQuery);

    /**
     * 保存系统配置
     *
     * @param sysConfigForm 系统配置表单
     * @return 是否保存成功
     */
    boolean save(ConfigForm sysConfigForm);

    /**
     * 获取系统配置表单数据
     *
     * @param id 系统配置ID
     * @return 系统配置表单数据
     */
    ConfigForm getConfigFormData(Long id);

    /**
     * 编辑系统配置
     *
     * @param id            系统配置ID
     * @param sysConfigForm 系统配置表单
     * @return 是否编辑成功
     */
    boolean edit(Long id, ConfigForm sysConfigForm);

    /**
     * 删除系统配置
     *
     * @param ids 系统配置ID
     * @return 是否删除成功
     */
    boolean delete(Long ids);

    /**
     * 刷新系统配置缓存
     *
     * @return 是否刷新成功
     */
    boolean refreshCache();


}
