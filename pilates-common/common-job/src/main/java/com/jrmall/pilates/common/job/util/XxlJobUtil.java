package com.jrmall.pilates.common.job.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.jrmall.pilates.common.job.config.XxlJobProperties;
import com.jrmall.pilates.common.job.constant.JobConstant;
import com.jrmall.pilates.common.job.model.XxlJobInfoBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * XXL Job 工具类
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/4 16:17
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class XxlJobUtil {

    private final XxlJobProperties xxlJobProperties;

    /**
     * 添加任务
     *
     * @param jobInfoBo 任务信息
     * @return 添加结果
     */
    public boolean addJob(XxlJobInfoBo jobInfoBo) {
        log.info("添加任务：{}", jobInfoBo);
        jobInfoBo.setJobGroup(xxlJobProperties.getJobGroup());
        String url = xxlJobProperties.getAddresses() + JobConstant.Api.ADD;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, xxlJobProperties.getAccessToken())
                .body(JSONUtil.toJsonStr(jobInfoBo))
                .timeout(xxlJobProperties.getTimeout())
                .execute();
        if (!httpResponse.isOk()) {
            log.error("添加任务失败：{},jobInfoBo:{}", httpResponse.body(), jobInfoBo);
            return false;
        }
        return true;
    }

    /**
     * 移除任务
     *
     * @param jobId 任务ID
     * @return 移除结果
     */
    public boolean removeJob(int jobId) {
        String url = xxlJobProperties.getAddresses() + JobConstant.Api.REMOVE;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, xxlJobProperties.getAccessToken())
                .form("id", jobId)
                .timeout(xxlJobProperties.getTimeout())
                .execute();
        if (!httpResponse.isOk()) {
            log.error("移除任务失败：{},jobId:{}", httpResponse.body(), jobId);
            return false;
        }
        return true;
    }

    /**
     * 停止任务
     *
     * @param jobId 任务ID
     * @return 停止结果
     */
    public boolean stopJob(int jobId) {
        String url = xxlJobProperties.getAddresses() + JobConstant.Api.STOP;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, xxlJobProperties.getAccessToken())
                .form("id", jobId)
                .timeout(xxlJobProperties.getTimeout())
                .execute();
        if (!httpResponse.isOk()) {
            log.error("停止任务失败：{},jobId:{}", httpResponse.body(), jobId);
            return false;
        }
        return true;
    }

    /**
     * 启动任务
     *
     * @param jobId 任务ID
     * @return 启动结果
     */
    public boolean startJob(int jobId) {
        String url = xxlJobProperties.getAddresses() + JobConstant.Api.START;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, xxlJobProperties.getAccessToken())
                .form("id", jobId)
                .timeout(xxlJobProperties.getTimeout())
                .execute();
        if (!httpResponse.isOk()) {
            log.error("启动任务失败：{},jobId:{}", httpResponse.body(), jobId);
            return false;
        }
        return true;
    }

}
