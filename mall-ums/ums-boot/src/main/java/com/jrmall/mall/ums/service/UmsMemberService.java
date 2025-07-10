package com.jrmall.mall.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jrmall.mall.ums.model.dto.MemberAddressDTO;
import com.jrmall.mall.ums.model.dto.MemberAuthDTO;
import com.jrmall.mall.ums.model.dto.MemberRegisterDto;
import com.jrmall.mall.ums.model.entity.UmsMember;
import com.jrmall.mall.ums.model.vo.MemberVO;
import com.jrmall.mall.ums.model.vo.ProductHistoryVO;

import java.util.List;
import java.util.Set;

/**
 * 会员业务接口
 *
 * @author haoxr
 * @since 2022/2/12
 */
public interface UmsMemberService extends IService<UmsMember> {

    IPage<UmsMember> list(Page<UmsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    MemberAuthDTO getMemberByOpenid(String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    MemberAuthDTO getMemberByMobile(String mobile);

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    Long addMember(MemberRegisterDto member);

    /**
     * 修改会员状态
     *
     * @param memberId
     * @param status
     * @return
     */
    boolean updateMemberStatus(Long memberId, Integer status);

    /**
     * 删除会员
     *
     * @param memberIds
     * @return
     */
    boolean updateMemberDeleted(String memberIds);

    /**
     * 获取登录会员信息
     *
     * @return
     */
    MemberVO getCurrMemberInfo();

    /**
     * 获取会员地址列表
     *
     * @param memberId
     * @return
     */
    List<MemberAddressDTO> listMemberAddress(Long memberId);


}
