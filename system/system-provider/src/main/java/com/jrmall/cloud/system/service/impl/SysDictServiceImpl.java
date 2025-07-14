package com.jrmall.cloud.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jrmall.cloud.common.base.Option;
import com.jrmall.cloud.common.exception.ProviderException;
import com.jrmall.cloud.system.converter.DictConverter;
import com.jrmall.cloud.system.mapper.SysDictMapper;
import com.jrmall.cloud.system.model.entity.SysDict;
import com.jrmall.cloud.system.model.entity.SysDictItem;
import com.jrmall.cloud.system.model.form.DictForm;
import com.jrmall.cloud.system.model.query.DictPageQuery;
import com.jrmall.cloud.system.model.vo.DictPageVO;
import com.jrmall.cloud.system.service.SysDictItemService;
import com.jrmall.cloud.system.service.SysDictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典业务实现类
 *
 * @author haoxr
 * @since 2022/10/12
 */
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    private final SysDictItemService dictItemService;
    private final DictConverter dictConverter;

    /**
     * 字典分页列表
     *
     * @param queryParams 分页查询对象
     */
    @Override
    public Page<DictPageVO> getDictPage(DictPageQuery queryParams) {
        // 查询参数
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();

        // 查询数据
        return this.baseMapper.getDictPage(new Page<>(pageNum, pageSize), queryParams);
    }

    /**
     * 获取字典列表
     *
     * @return 字典列表
     */
    @Override
    public List<Option<String>> getDictList() {
        return this.list(new LambdaQueryWrapper<SysDict>().eq(SysDict::getStatus, 1))
                .stream()
                .map(item -> new Option<>(item.getDictCode(), item.getName()))
                .toList();
    }


    /**
     * 新增字典
     *
     * @param dictForm 字典表单数据
     */
    @Override
    public boolean saveDict(DictForm dictForm) {
        // 保存字典
        SysDict entity = dictConverter.toEntity(dictForm);

        // 校验 code 是否唯一
        String dictCode = entity.getDictCode();

        long count = this.count(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictCode, dictCode)
        );

        Assert.isTrue(count == 0, "字典编码已存在");

        return this.save(entity);
    }


    /**
     * 获取字典表单详情
     *
     * @param id 字典ID
     */
    @Override
    public DictForm getDictForm(Long id) {
        // 获取字典
        SysDict entity = this.getById(id);
        if (entity == null) {
            throw new ProviderException("字典不存在");
        }
        return dictConverter.toForm(entity);
    }

    /**
     * 修改字典
     *
     * @param id       字典ID
     * @param dictForm 字典表单
     */
    @Override
    public boolean updateDict(Long id, DictForm dictForm) {
        // 获取字典
        SysDict entity = this.getById(id);
        if (entity == null) {
            throw new ProviderException("字典不存在");
        }
        // 校验 code 是否唯一
        String dictCode = dictForm.getDictCode();
        if (!entity.getDictCode().equals(dictCode)) {
            long count = this.count(new LambdaQueryWrapper<SysDict>()
                    .eq(SysDict::getDictCode, dictCode)
            );
            Assert.isTrue(count == 0, "字典编码已存在");
        }
        // 更新字典
        SysDict dict = dictConverter.toEntity(dictForm);
        dict.setId(id);
        return this.updateById(dict);
    }

    /**
     * 删除字典
     *
     * @param ids 字典ID，多个以英文逗号(,)分割
     */
    @Transactional
    @Override
    public void deleteDictByIds(List<String> ids) {
        // 删除字典
        this.removeByIds(ids);

        // 删除字典项
        List<SysDict> list = this.listByIds(ids);
        if (!list.isEmpty()) {
            List<String> dictCodes = list.stream().map(SysDict::getDictCode).toList();
            dictItemService.remove(new LambdaQueryWrapper<SysDictItem>()
                    .in(SysDictItem::getDictCode, dictCodes)
            );
        }
    }

    /**
     * 根据字典ID列表获取字典编码列表
     *
     * @param ids 字典ID列表
     * @return 字典编码列表
     */
    @Override
    public List<String> getDictCodesByIds(List<String> ids) {
        List<SysDict> dictList = this.listByIds(ids);
        return dictList.stream().map(SysDict::getDictCode).toList();
    }

}




