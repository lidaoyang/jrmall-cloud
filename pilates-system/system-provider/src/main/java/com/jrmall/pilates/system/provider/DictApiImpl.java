package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.DictApi;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.form.DictItemForm;
import com.jrmall.pilates.system.model.query.DictItemPageQuery;
import com.jrmall.pilates.system.model.query.DictPageQuery;
import com.jrmall.pilates.system.model.vo.DictItemOptionVO;
import com.jrmall.pilates.system.model.vo.DictItemPageVO;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import com.jrmall.pilates.system.service.SysDictItemService;
import com.jrmall.pilates.system.service.SysDictService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * 字典接口实现
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/6 17:01
 */
@DubboService
public class DictApiImpl implements DictApi {

    @Resource
    private SysDictService dictService;

    @Resource
    private SysDictItemService dictItemService;

    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        return dictService.getDictPage(queryParams);
    }

    @Override
    public List<Option<String>> getDictList() {
        return dictService.getDictList();
    }

    @Override
    public DictForm getDictForm(Long id) {
        return dictService.getDictForm(id);
    }

    @Override
    public boolean saveDict(DictForm dictForm) {
        return dictService.saveDict(dictForm);
    }

    @Override
    public boolean updateDict(Long id, DictForm dictForm) {
        return dictService.updateDict(id, dictForm);
    }

    @Override
    public void deleteDictByIds(List<String> ids) {
        dictService.deleteDictByIds(ids);
    }

    @Override
    public List<String> getDictCodesByIds(List<String> ids) {
        return dictService.getDictCodesByIds(ids);
    }

    @Override
    public Page<DictItemPageVO> getDictItemPage(DictItemPageQuery queryParams) {
        return dictItemService.getDictItemPage(queryParams);
    }

    @Override
    public List<DictItemOptionVO> getDictItems(String dictCode) {
        return dictItemService.getDictItems(dictCode);
    }

    @Override
    public DictItemForm getDictItemForm(Long itemId) {
        return dictItemService.getDictItemForm(itemId);
    }

    @Override
    public boolean saveDictItem(DictItemForm formData) {
        return dictItemService.saveDictItem(formData);
    }

    @Override
    public boolean updateDictItem(DictItemForm formData) {
        return dictItemService.updateDictItem(formData);
    }

    @Override
    public void deleteDictItemByIds(String ids) {
        dictItemService.deleteDictItemByIds(ids);
    }

}
