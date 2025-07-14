package com.jrmall.mall.ums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jrmall.mall.ums.convert.AddressConvert;
import com.jrmall.mall.ums.convert.MemberConvert;
import com.jrmall.mall.ums.mapper.UmsMemberMapper;
import com.jrmall.mall.ums.model.dto.MemberAddressDTO;
import com.jrmall.mall.ums.model.dto.MemberAuthDTO;
import com.jrmall.mall.ums.model.dto.MemberRegisterDto;
import com.jrmall.mall.ums.model.entity.UmsAddress;
import com.jrmall.mall.ums.model.entity.UmsMember;
import com.jrmall.mall.ums.model.vo.MemberVO;
import com.jrmall.mall.ums.model.vo.ProductHistoryVO;
import com.jrmall.mall.ums.service.UmsAddressService;
import com.jrmall.mall.ums.service.UmsMemberService;
import com.jrmall.cloud.common.constant.GlobalConstants;
import com.jrmall.cloud.common.constant.MemberConstants;
import com.jrmall.cloud.common.exception.BizException;
import com.jrmall.cloud.common.job.em.AlarmTypeEnum;
import com.jrmall.cloud.common.job.em.ScheduleTypeEnum;
import com.jrmall.cloud.common.job.model.XxlJobInfoBo;
import com.jrmall.cloud.common.job.util.XxlJobUtil;
import com.jrmall.cloud.common.redis.util.RedisUtilO;
import com.jrmall.cloud.common.result.ResultCode;
import com.jrmall.cloud.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 会员业务实现类
 *
 * @author haoxr
 * @since 2022/2/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    private final RedisUtilO redisUtil;
    private final MemberConvert memberConvert;

    private final AddressConvert addressConvert;
    private final UmsAddressService addressService;

    private final XxlJobUtil xxlJobUtil;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, String nickname) {
        List<UmsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = MemberConstants.USER_PRODUCT_HISTORY + userId;
            redisUtil.zAdd(key, product, System.currentTimeMillis());
            Long size = redisUtil.zSize(key);
            if (size > 10) {
                redisUtil.removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        Set<Object> objectSet = redisUtil.reverseRange(MemberConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
        return objectSet.stream().map(object -> (ProductHistoryVO)object).collect(Collectors.toSet());
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid 微信唯一身份标识
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByOpenid(String openid) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid)
                .select(UmsMember::getId,
                        UmsMember::getOpenid,
                        UmsMember::getStatus
                )
        );
        
        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2OpenidAuthDTO(entity);
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByMobile(String mobile) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getMobile, mobile)
                .select(UmsMember::getId,
                        UmsMember::getMobile,
                        UmsMember::getStatus
                )
        );

        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2MobileAuthDTO(entity);
    }

    /**
     * 新增会员
     *
     * @param memberRegisterDTO
     * @return
     */
    @Override
    public Long addMember(MemberRegisterDto memberRegisterDTO) {
        UmsMember umsMember = memberConvert.dto2Entity(memberRegisterDTO);
        boolean result = this.save(umsMember);
        Assert.isTrue(result, "新增会员失败");
        return umsMember.getId();
    }

    @Override
    public boolean updateMemberStatus(Long memberId, Integer status) {
        boolean updated = this.update(
                new LambdaUpdateWrapper<UmsMember>()
                        .eq(UmsMember::getId, memberId)
                        .set(UmsMember::getStatus, status)
        );
        if (updated && status==0) {
            // 添加更新定时任务 //TODO 测试定时任务
            addXxlJobForUpdateStatus(memberId, status);
        }
        return updated;
    }

    private void addXxlJobForUpdateStatus(Long memberId, Integer status) {
        XxlJobInfoBo jobInfo = new XxlJobInfoBo();
        jobInfo.setJobDesc("修改会员状态任务");
        jobInfo.setScheduleType(ScheduleTypeEnum.NONE.name());
        jobInfo.setExecutorHandler("umsMemberStatusUpdate");
        jobInfo.setExecutorParam(JSON.toJSONString(Map.of("memberId", memberId.intValue(), "status", status)));
        jobInfo.setAlarmType(AlarmTypeEnum.NOT.getAlarmType());
        jobInfo.setTriggerNextTime(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());// 2分钟后执行
        xxlJobUtil.addJob(jobInfo);
    }

    @Override
    public boolean updateMemberDeleted(String memberIds) {
        return this.update(new LambdaUpdateWrapper<UmsMember>()
                .in(UmsMember::getId, Arrays.asList(memberIds.split(",")))
                .set(UmsMember::getDeleted, GlobalConstants.STATUS_YES));
    }

    /**
     * 获取登录会员信息
     *
     * @return
     */
    @Override
    public MemberVO getCurrMemberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        UmsMember umsMember = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getId,
                        UmsMember::getNickName,
                        UmsMember::getAvatarUrl,
                        UmsMember::getMobile,
                        UmsMember::getBalance
                )
        );
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(umsMember, memberVO);
        return memberVO;
    }

    /**
     * 获取会员地址
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {

        List<UmsAddress> entities = addressService.list(
                new LambdaQueryWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, memberId)
        );

        List<MemberAddressDTO> list = addressConvert.entity2Dto(entities);
        return list;
    }
}
