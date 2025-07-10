package com.xxl.job.executor.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jrmall.mall.ums.api.UmsMemberApi;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 会员服务相关任务
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/7 10:09
 */
@Slf4j
@Component
public class MallUmsJob {

    @DubboReference
    private UmsMemberApi memberApi;

    /**
     * 会员状态更新
     */
    @XxlJob("umsMemberStatusUpdate")
    public void umsMemberStatusUpdateHandler() {

        int jobId = (int) XxlJobHelper.getJobId();
        JSONObject paramJ = JSON.parseObject(Objects.requireNonNull(XxlJobHelper.getJobParam()));
        XxlJobHelper.log("开始执行会员状态更新任务,jobId:{},param:{}", jobId, XxlJobHelper.getJobParam());
        Boolean ret = memberApi.updateMemberStatus(paramJ.getLong("memberId"), paramJ.getInteger("status") == 1 ? 1 : 0);
        XxlJobHelper.handleResult(ret ? 200 : 500, "会员状态更新任务执行完成");
    }


}
