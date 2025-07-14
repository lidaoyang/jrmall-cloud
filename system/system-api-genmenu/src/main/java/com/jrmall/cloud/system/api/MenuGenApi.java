package com.jrmall.cloud.system.api;

import com.jrmall.cloud.system.model.form.MenuGenConfigForm;

/**
 * 系统配置接口
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/16 15:34
 */
public interface MenuGenApi {

    /**
     * 代码生成时添加菜单
     *
     * @param parentMenuId 父菜单ID
     * @param menuGenConfigForm    实体名
     */
    void addMenuForCodegen(Long parentMenuId, MenuGenConfigForm menuGenConfigForm);


}
