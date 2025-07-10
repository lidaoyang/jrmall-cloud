package com.jrmall.mall.ums.api;

/**
 * 会员接口
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/9 14:13
 */
public interface UmsMemberApi {

    Boolean updateMemberStatus(Long memberId, Integer status);
}
