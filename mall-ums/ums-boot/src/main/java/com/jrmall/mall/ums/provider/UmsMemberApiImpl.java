package com.jrmall.mall.ums.provider;

import com.jrmall.mall.ums.api.UmsMemberApi;
import com.jrmall.mall.ums.service.UmsMemberService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author: Dao-yang.
 * @date: Created in 2025/7/9 14:17
 */
@DubboService
public class UmsMemberApiImpl implements UmsMemberApi {

    @Resource
    private UmsMemberService umsMemberService;

    @Override
    public Boolean updateMemberStatus(Long memberId, Integer status) {
        return umsMemberService.updateMemberStatus(memberId, status);
    }
}
