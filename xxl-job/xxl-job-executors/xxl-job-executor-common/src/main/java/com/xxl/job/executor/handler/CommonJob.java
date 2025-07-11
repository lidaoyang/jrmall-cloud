package com.xxl.job.executor.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 通用任务
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/7 10:09
 */
@Component
public class CommonJob {

    /**
     * 结束任务执行器
     */
    @XxlJob("endExecutorHandler")
    public void endExecutorHandler() {

        int jobId = (int) XxlJobHelper.getJobId();
        int jobParam = Integer.parseInt(Objects.requireNonNull(XxlJobHelper.getJobParam()));
        XxlJobHelper.log("开始执行结束任务,jobId:{},endJobId:{}", jobId,jobParam);
        try {
            ReturnT<String> returnT = XxlJobExecutor.getAdminBizList().get(0).stopJob(jobParam);
            if (returnT.getCode() == ReturnT.SUCCESS_CODE) {
                XxlJobHelper.log("任务停止成功");
            }else {
                XxlJobHelper.log("任务停止失败");

            }
        } catch (Exception e) {
            XxlJobHelper.log("停止任务异常,jobId:{},endJobId:{},msg:{}", jobId, jobParam, e.getMessage());
        } finally {
            XxlJobHelper.log("开始停止自己,jobId:{}", jobId);
            XxlJobExecutor.getAdminBizList().get(0).stopJob(jobId);
        }
    }


}
