package com.jrmall.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jrmall.cloud.system.converter.DictItemConverter;
import com.jrmall.cloud.system.mapper.SysDictItemMapper;
import com.jrmall.cloud.system.model.entity.SysDictItem;
import com.jrmall.cloud.system.model.form.DictItemForm;
import com.jrmall.cloud.system.model.query.DictItemPageQuery;
import com.jrmall.cloud.system.model.vo.DictItemOptionVO;
import com.jrmall.cloud.system.model.vo.DictItemPageVO;
import com.jrmall.cloud.system.service.SysDictItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 字典项实现类
 *
 * @author Ray.Hao
 * @since 2022/10/12
 */
@Service
@RequiredArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    private final DictItemConverter dictItemConverter;

    /**
     * 获取字典项分页列表
     *
     * @param queryParams 查询参数
     * @return 字典项分页列表
     */
    @Override
    public Page<DictItemPageVO> getDictItemPage(DictItemPageQuery queryParams) {
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<DictItemPageVO> page = new Page<>(pageNum, pageSize);

        return this.baseMapper.getDictItemPage(page, queryParams);
    }


    /**
     * 获取字典项列表
     *
     * @param dictCode 字典编码
     */
    @Override
    public List<DictItemOptionVO> getDictItems(String dictCode) {
        return this.list(
                        new LambdaQueryWrapper<SysDictItem>()
                                .eq(SysDictItem::getDictCode, dictCode)
                                .eq(SysDictItem::getStatus, 1)
                                .orderByAsc(SysDictItem::getSort)
                ).stream()
                .map(item -> {
                    DictItemOptionVO dictItemOptionVO = new DictItemOptionVO();
                    dictItemOptionVO.setLabel(item.getLabel());
                    dictItemOptionVO.setValue(item.getValue());
                    dictItemOptionVO.setTagType(item.getTagType());
                    return dictItemOptionVO;
                }).toList();
    }


    /**
     * 获取字典项表单
     *
     * @param itemId 字典项ID
     * @return 字典项表单
     */
    @Override
    public DictItemForm getDictItemForm(Long itemId) {
        SysDictItem entity = this.getById(itemId);
        return dictItemConverter.toForm(entity);
    }

    /**
     * 保存字典项
     *
     * @param formData 字典项表单
     * @return 是否成功
     */
    @Override
    public boolean saveDictItem(DictItemForm formData) {
        SysDictItem entity = dictItemConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新字典项
     *
     * @param formData 字典项表单
     * @return 是否成功
     */
    @Override
    public boolean updateDictItem(DictItemForm formData) {
        SysDictItem entity = dictItemConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除字典项
     *
     * @param ids 字典项ID集合
     */
    @Override
    public void deleteDictItemByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        this.removeByIds(idList);
    }

}




