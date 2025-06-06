package com.jrmall.pilates.system.provider;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.api.DictApi;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.form.DictTypeForm;
import com.jrmall.pilates.system.model.query.DictPageQuery;
import com.jrmall.pilates.system.model.query.DictTypePageQuery;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import com.jrmall.pilates.system.model.vo.DictTypePageVO;
import com.jrmall.pilates.system.service.SysDictService;
import com.jrmall.pilates.system.service.SysDictTypeService;
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
    private SysDictTypeService dictTypeService;

    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        return dictService.getDictPage(queryParams);
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
    public boolean deleteDict(String idsStr) {
        return dictService.deleteDict(idsStr);
    }

    @Override
    public List<Option<String>> listDictOptions(String typeCode) {
        return dictService.listDictOptions(typeCode);
    }

    @Override
    public Page<DictTypePageVO> getDictTypePage(DictTypePageQuery queryParams) {
        return dictTypeService.getDictTypePage(queryParams);
    }

    @Override
    public DictTypeForm getDictTypeForm(Long id) {
        return dictTypeService.getDictTypeForm(id);
    }

    @Override
    public boolean saveDictType(DictTypeForm dictTypeForm) {
        return dictTypeService.saveDictType(dictTypeForm);
    }

    @Override
    public boolean updateDictType(Long id, DictTypeForm dictTypeForm) {
        return dictTypeService.updateDictType(id, dictTypeForm);
    }

    @Override
    public boolean deleteDictTypes(String idsStr) {
        return dictTypeService.deleteDictTypes(idsStr);
    }

    @Override
    public List<Option<String>> listDictItemsByTypeCode(String typeCode) {
        return dictTypeService.listDictItemsByTypeCode(typeCode);
    }
}
