package com.jrmall.cloud.system.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.cloud.common.base.Option;
import com.jrmall.cloud.system.model.form.DictForm;
import com.jrmall.cloud.system.model.form.DictItemForm;
import com.jrmall.cloud.system.model.query.DictItemPageQuery;
import com.jrmall.cloud.system.model.query.DictPageQuery;
import com.jrmall.cloud.system.model.vo.DictItemOptionVO;
import com.jrmall.cloud.system.model.vo.DictItemPageVO;
import com.jrmall.cloud.system.model.vo.DictPageVO;

import java.util.List;

/**
 * 字典接口
 */
public interface DictApi {

    /**
     * 获取字典分页列表
     *
     * @param queryParams 分页查询对象
     * @return 字典分页列表
     */
    Page<DictPageVO> getDictPage(DictPageQuery queryParams);

    /**
     * 获取字典列表
     *
     * @return 字典列表
     */
    List<Option<String>> getDictList();

    /**
     * 获取字典表单数据
     *
     * @param id 字典ID
     * @return 字典表单
     */
    DictForm getDictForm(Long id);

    /**
     * 新增字典
     *
     * @param dictForm 字典表单
     * @return 是否成功
     */
    boolean saveDict(DictForm dictForm);

    /**
     * 修改字典
     *
     * @param id       字典ID
     * @param dictForm 字典表单
     * @return 是否成功
     */
    boolean updateDict(Long id, DictForm dictForm);

    /**
     * 删除字典
     *
     * @param ids 字典ID集合
     */
    void deleteDictByIds(List<String> ids);

    /**
     * 根据字典ID列表获取字典编码列表
     *
     * @param ids 字典ID列表
     * @return 字典编码列表
     */
    List<String> getDictCodesByIds(List<String> ids);

    /**
     * 字典项分页列表
     *
     * @param queryParams 查询参数
     * @return 字典项分页列表
     */
    Page<DictItemPageVO> getDictItemPage(DictItemPageQuery queryParams);

    /**
     * 获取字典项列表
     *
     * @param dictCode 字典编码
     * @return 字典项列表
     */
    List<DictItemOptionVO> getDictItems(String dictCode);

    /**
     * 获取字典项表单
     *
     * @param itemId 字典项ID
     * @return 字典项表单
     */
    DictItemForm getDictItemForm(Long itemId);

    /**
     * 保存字典项
     *
     * @param formData 字典项表单
     * @return 是否成功
     */
    boolean saveDictItem(DictItemForm formData);

    /**
     * 更新字典项
     *
     * @param formData 字典项表单
     * @return 是否成功
     */
    boolean updateDictItem(DictItemForm formData);

    /**
     * 删除字典项
     *
     * @param ids 字典项ID,多个逗号分隔
     */
    void deleteDictItemByIds(String ids);

}
