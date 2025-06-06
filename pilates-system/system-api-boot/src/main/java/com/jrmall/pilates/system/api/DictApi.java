package com.jrmall.pilates.system.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.base.Option;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.form.DictTypeForm;
import com.jrmall.pilates.system.model.query.DictPageQuery;
import com.jrmall.pilates.system.model.query.DictTypePageQuery;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import com.jrmall.pilates.system.model.vo.DictTypePageVO;

import java.util.List;

/**
 * 字典接口
 */
public interface DictApi {
    /**
     * 字典数据项分页列表
     *
     * @param queryParams
     * @return
     */
    Page<DictPageVO> getDictPage(DictPageQuery queryParams);

    /**
     * 字典数据项表单
     *
     * @param id 字典数据项ID
     * @return
     */
    DictForm getDictForm(Long id);

    /**
     * 新增字典数据项
     *
     * @param dictForm 字典数据项表单
     * @return
     */
    boolean saveDict(DictForm dictForm);

    /**
     * 修改字典数据项
     *
     * @param id       字典数据项ID
     * @param dictForm 字典数据项表单
     * @return
     */
    boolean updateDict(Long id, DictForm dictForm);

    /**
     * 删除字典数据项
     *
     * @param idsStr 字典数据项ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteDict(String idsStr);

    /**
     * 获取字典下拉列表
     *
     * @param typeCode
     * @return
     */
    List<Option<String>> listDictOptions(String typeCode);

    /*-----------------------------↓字典类型接口----------------------------*/
    /**
     * 字典类型分页列表
     *
     * @param queryParams 分页查询对象
     * @return
     */
    Page<DictTypePageVO> getDictTypePage(DictTypePageQuery queryParams);


    /**
     * 获取字典类型表单详情
     *
     * @param id 字典类型ID
     * @return
     */
    DictTypeForm getDictTypeForm(Long id);


    /**
     * 新增字典类型
     *
     * @param dictTypeForm 字典类型表单
     * @return
     */
    boolean saveDictType(DictTypeForm dictTypeForm);


    /**
     * 修改字典类型
     *
     * @param id
     * @param dictTypeForm 字典类型表单
     * @return
     */
    boolean updateDictType(Long id, DictTypeForm dictTypeForm);

    /**
     * 删除字典类型
     *
     * @param idsStr 字典类型ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteDictTypes(String idsStr);


    /**
     * 获取字典类型的数据项
     *
     * @param typeCode
     * @return
     */
    List<Option<String>> listDictItemsByTypeCode(String typeCode);

}
