package com.jrmall.pilates.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import com.jrmall.pilates.common.web.annotation.PreventDuplicateResubmit;
import com.jrmall.pilates.system.api.DictApi;
import com.jrmall.pilates.system.model.form.DictForm;
import com.jrmall.pilates.system.model.form.DictTypeForm;
import com.jrmall.pilates.system.model.query.DictPageQuery;
import com.jrmall.pilates.system.model.query.DictTypePageQuery;
import com.jrmall.pilates.system.model.vo.DictPageVO;
import com.jrmall.pilates.system.model.vo.DictTypePageVO;
import com.jrmall.pilates.common.base.Option;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "字典分页列表")
    @GetMapping("/page")
    public PageResult<DictPageVO> getDictPage(
            @ParameterObject DictPageQuery queryParams
    ) {
        Page<DictPageVO> result = dictApi.getDictPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典数据表单数据")
    @GetMapping("/{id}/form")
    public Result<DictForm> getDictForm(
            @Parameter(description = "字典ID") @PathVariable Long id
    ) {
        DictForm formData = dictApi.getDictForm(id);
        return Result.success(formData);
    }

    @Operation(summary = "新增字典")
    @PostMapping("/add")
    @PreAuthorize("@ss.hasPerm('sys:dict:add')")
    @PreventDuplicateResubmit
    public Result<Boolean> saveDict(
            @RequestBody DictForm DictForm
    ) {
        boolean result = dictApi.saveDict(DictForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典")
    @PutMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict:edit')")
    public Result<Boolean> updateDict(
            @PathVariable Long id,
            @RequestBody DictForm DictForm
    ) {
        boolean status = dictApi.updateDict(id, DictForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict:delete')")
    public Result<Boolean> deleteDict(
            @Parameter(description = "字典ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean result = dictApi.deleteDict(ids);
        return Result.judge(result);
    }


    @Operation(summary = "字典下拉列表")
    @GetMapping("/options")
    public Result<List<Option<String>>> listDictOptions(
            @Parameter(description = "字典类型编码") @RequestParam String typeCode
    ) {
        List<Option<String>> list = dictApi.listDictOptions(typeCode);
        return Result.success(list);
    }


    /*----------------------------------------------------*/
    @Operation(summary = "字典类型分页列表")
    @GetMapping("/types/page")
    public PageResult<DictTypePageVO> getDictTypePage(
            @ParameterObject DictTypePageQuery queryParams
    ) {
        Page<DictTypePageVO> result = dictApi.getDictTypePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "字典类型表单数据")
    @GetMapping("/types/{id}/form")
    public Result<DictTypeForm> getDictTypeForm(
            @Parameter(description = "字典ID") @PathVariable Long id
    ) {
        DictTypeForm dictTypeForm = dictApi.getDictTypeForm(id);
        return Result.success(dictTypeForm);
    }

    @Operation(summary = "新增字典类型")
    @PostMapping("/types/add")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:add')")
    @PreventDuplicateResubmit
    public Result<Boolean> saveDictType(@RequestBody DictTypeForm dictTypeForm) {
        boolean result = dictApi.saveDictType(dictTypeForm);
        return Result.judge(result);
    }

    @Operation(summary = "修改字典类型")
    @PutMapping("/types/{id}")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:edit')")
    public Result<Boolean> updateDictType(@PathVariable Long id, @RequestBody DictTypeForm dictTypeForm) {
        boolean status = dictApi.updateDictType(id, dictTypeForm);
        return Result.judge(status);
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/types/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dict_type:delete')")
    public Result<Boolean> deleteDictTypes(
            @Parameter(description = "字典类型ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = dictApi.deleteDictTypes(ids);
        return Result.judge(result);
    }

    @Operation(summary = "获取字典类型的数据项")
    @GetMapping("/types/{typeCode}/items")
    public Result<List<Option<String>>> listDictTypeItems(
            @Parameter(description = "字典类型编码") @PathVariable String typeCode
    ) {
        List<Option<String>> list = dictApi.listDictItemsByTypeCode(typeCode);
        return Result.success(list);
    }


}
