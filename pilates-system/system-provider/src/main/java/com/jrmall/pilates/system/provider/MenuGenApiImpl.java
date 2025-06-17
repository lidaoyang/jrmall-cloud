package com.jrmall.pilates.system.provider;

import com.jrmall.pilates.system.api.MenuGenApi;
import com.jrmall.pilates.system.model.form.MenuGenConfigForm;
import com.jrmall.pilates.system.service.SysMenuService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * 菜单生成接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/17 14:15
 */
@DubboService
public class MenuGenApiImpl implements MenuGenApi {

    @Resource
    private SysMenuService sysMenuService;

    @Override
    public void addMenuForCodegen(Long parentMenuId, MenuGenConfigForm genConfig) {
        sysMenuService.addMenuForCodegen(parentMenuId, genConfig);
    }
}
