package com.jrmall.mall.ums.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jrmall.mall.ums.model.entity.UmsMember;
import com.jrmall.mall.ums.service.UmsMemberService;
import com.jrmall.pilates.common.result.PageResult;
import com.jrmall.pilates.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Admin-会员管理")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UmsMemberController {

    private final UmsMemberService memberService;

    @Operation(summary= "会员分页列表")
    @GetMapping
    public PageResult<UmsMember> getMemberPage(
            @Parameter(name = "页码") Long pageNum,
            @Parameter(name = "每页数量") Long pageSize,
            @Parameter(name = "会员昵称") String nickName
    ) {
        IPage<UmsMember> result = memberService.list(new Page<>(pageNum, pageSize), nickName);
        return PageResult.success(result);
    }

    @Operation(summary= "修改会员")
    @PutMapping(value = "/{memberId}")
    public <T> Result<T> update(
            @Parameter(name = "会员ID") @PathVariable Long memberId,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.updateById(member);
        return Result.judge(status);
    }

    @Operation(summary= "修改会员状态")
    @PatchMapping("/{memberId}/status")
    public <T> Result<T> updateMemberStatus(
            @Parameter(description = "会员ID") @PathVariable Long memberId,
            @Parameter(description = "会员状态(1:启用;0:禁用)") @RequestParam Integer status
    ) {
        return Result.judge(memberService.updateMemberStatus(memberId, status));
    }

    @Operation(summary= "删除会员")
    @DeleteMapping("/{ids}")
    public <T> Result<T> delete(
            @Parameter(name = "会员ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {

        return Result.judge(memberService.updateMemberDeleted(ids));
    }


}
