package com.jrmall.cloud.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jrmall.cloud.common.result.PageResult;
import com.jrmall.cloud.common.result.Result;
import com.jrmall.cloud.system.api.NoticeApi;
import com.jrmall.cloud.system.model.form.NoticeForm;
import com.jrmall.cloud.system.model.query.NoticePageQuery;
import com.jrmall.cloud.system.model.vo.NoticeDetailVO;
import com.jrmall.cloud.system.model.vo.NoticePageVO;
import com.jrmall.cloud.system.model.vo.UserNoticePageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告前端控制层
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Tag(name = "09.通知公告")
@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class SysNoticeController {

    @DubboReference
    private NoticeApi noticeApi;

    @Operation(summary = "通知公告分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('sys:notice:query')")
    public PageResult<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        IPage<NoticePageVO> result = noticeApi.getNoticePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "新增通知公告")
    @PostMapping("add")
    @PreAuthorize("@ss.hasPerm('sys:notice:add')")
    public Result<?> saveNotice(@RequestBody @Valid NoticeForm formData) {
        boolean result = noticeApi.saveNotice(formData);
        return Result.judge(result);
    }

    @Operation(summary = "获取通知公告表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<NoticeForm> getNoticeForm(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        NoticeForm formData = noticeApi.getNoticeFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "阅读获取通知公告详情")
    @GetMapping("/{id}/detail")
    public Result<NoticeDetailVO> getNoticeDetail(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        NoticeDetailVO detailVO = noticeApi.getNoticeDetail(id);
        return Result.success(detailVO);
    }

    @Operation(summary = "修改通知公告")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:notice:edit')")
    public Result<Void> updateNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id,
            @RequestBody @Validated NoticeForm formData
    ) {
        boolean result = noticeApi.updateNotice(id, formData);
        return Result.judge(result);
    }

    @Operation(summary = "发布通知公告")
    @PutMapping("/{id}/publish")
    @PreAuthorize("@ss.hasPerm('sys:notice:publish')")
    public Result<Void> publishNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        boolean result = noticeApi.publishNotice(id);
        return Result.judge(result);
    }

    @Operation(summary = "撤回通知公告")
    @PutMapping("/{id}/revoke")
    @PreAuthorize("@ss.hasPerm('sys:notice:revoke')")
    public Result<Void> revokeNotice(
            @Parameter(description = "通知公告ID") @PathVariable Long id
    ) {
        boolean result = noticeApi.revokeNotice(id);
        return Result.judge(result);
    }

    @Operation(summary = "删除通知公告")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:notice:delete')")
    public Result<Void> deleteNotices(
            @Parameter(description = "通知公告ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean result = noticeApi.deleteNotices(ids);
        return Result.judge(result);
    }

    @Operation(summary = "全部已读")
    @PutMapping("/read-all")
    public Result<Void> readAll() {
        noticeApi.readAll();
        return Result.success();
    }

    @Operation(summary = "获取我的通知公告分页列表")
    @GetMapping("/my-page")
    public PageResult<UserNoticePageVO> getMyNoticePage(
            NoticePageQuery queryParams
    ) {
        IPage<UserNoticePageVO> result = noticeApi.getMyNoticePage(queryParams);
        return PageResult.success(result);
    }
}
