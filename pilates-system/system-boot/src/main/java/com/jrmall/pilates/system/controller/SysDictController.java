package com.jrmall.pilates.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.pilates.system.api.DictApi;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.form.DictItemForm;
import com.jrmall.pilates.system.model.query.DictItemPageQuery;
import com.jrmall.pilates.system.model.query.DictPageQuery;
import com.jrmall.pilates.system.model.vo.DictItemOptionVO;
import com.jrmall.pilates.system.model.vo.DictItemPageVO;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import com.jrmall.pilates.common.base.Option;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 字典控制器
 *
 * @author haoxr
 * @since 2020/11/6
 */
@Tag(name = "05.字典接口")
@RestController
@RequestMapping("/api/v1/dict")
@RequiredArgsConstructor
public class SysDictController {

    @DubboReference
    private DictApi dictApi;

    //---------------------------------------------------
    // 字典相关接口
    //---------------------------------------------------
    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    public PageResult<DictPageVO> getDictPage(
            DictPageQuery queryParams) {
        Page<DictPageVO> result = dictApi.getDictPage(queryParams);
        return PageResult.success(result);
    }


    @Operation(summary = "字典列表")
    @GetMapping
    public Result<List<Option<String>>> getDictList() {
        List<Option<String>> list = dictApi.getDictList();
        return Result.success(list);
    }

    @Operation(summary = "获取字典表单数据")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(
            @Parameter(description = "字典ID") @PathVariable Long id) {
        DictForm formData = dictApi.getDictForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    @PreventDuplicateResubmit
    public Result<?> saveDict(@Valid @RequestBody DictForm formData) {
        boolean result = dictApi.saveDict(formData);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result<?> updateDict(
            @PathVariable Long id,
            @RequestBody DictForm dictForm) {
        boolean status = dictApi.updateDict(id, dictForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result<?> deleteDictionaries(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids) {
        // 获取字典编码列表，用于发送删除通知
        List<String> dictCodes = dictApi.getDictCodesByIds(Arrays.stream(ids.split(",")).toList());

        dictApi.deleteDictByIds(Arrays.stream(ids.split(",")).toList());
        return Result.success();
    }


    //---------------------------------------------------
    // 字典项相关接口
    //---------------------------------------------------
    @Operation(summary = "字典项分页列表")
    @GetMapping("/{dictCode}/item/page")
    public PageResult<DictItemPageVO> getDictItemPage(
            @PathVariable String dictCode,
            DictItemPageQuery queryParams) {
        queryParams.setDictCode(dictCode);
        Page<DictItemPageVO> result = dictApi.getDictItemPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典项列表")
    @GetMapping("/{dictCode}/item")
    public Result<List<DictItemOptionVO>> getDictItems(
            @Parameter(description = "字典编码") @PathVariable String dictCode) {
        List<DictItemOptionVO> list = dictApi.getDictItems(dictCode);
        return Result.success(list);
    }

    @Operation(summary = "新增字典项")
    @PostMapping("/{dictCode}/item")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:add')")
    @PreventDuplicateResubmit
    public Result<Void> saveDictItem(
            @PathVariable String dictCode,
            @Valid @RequestBody DictItemForm formData) {
        formData.setDictCode(dictCode);
        boolean result = dictApi.saveDictItem(formData);
        return Result.judge(result);
    }

    @Operation(summary = "字典项表单数据")
    @GetMapping("/{dictCode}/item/{itemId}/form")
    public Result<DictItemForm> getDictItemForm(
            @PathVariable String dictCode,
            @Parameter(description = "字典项ID") @PathVariable Long itemId) {
        DictItemForm formData = dictApi.getDictItemForm(itemId);
        return Result.success(formData);
    }

    @Operation(summary = "修改字典项")
    @PutMapping("/{dictCode}/item/{itemId}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:edit')")
    @PreventDuplicateResubmit
    public Result<?> updateDictItem(
            @PathVariable String dictCode,
            @PathVariable Long itemId,
            @RequestBody DictItemForm formData) {
        formData.setId(itemId);
        formData.setDictCode(dictCode);
        boolean status = dictApi.updateDictItem(formData);

        return Result.judge(status);
    }

    @Operation(summary = "删除字典项")
    @DeleteMapping("/{dictCode}/item/{itemIds}")
    @PreAuthorize("@ss.hasPerm('sys:dict-item:delete')")
    public Result<Void> deleteDictItems(
            @PathVariable String dictCode,
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String itemIds) {
        dictApi.deleteDictItemByIds(itemIds);
        return Result.success();
    }


}
