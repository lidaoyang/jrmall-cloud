package com.jrmall.pilates.common.job.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.jrmall.pilates.common.job.config.XxlJobConfig;
import com.jrmall.pilates.common.job.constant.JobConstant;
import com.jrmall.pilates.common.job.model.XxlJobInfoBo;
import lombok.extern.slf4j.Slf4j;

/**
 * XXL Job 工具类
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/4 16:17
 */
@Slf4j
public class XxlJobUtil {

    private static final XxlJobUtil INSTANCE = new XxlJobUtil();

    public static XxlJobUtil getInstance() {
        return INSTANCE;
    }

    private static XxlJobConfig jobConfig;

    public XxlJobUtil() {
        jobConfig = new XxlJobConfig();
    }

    /**
     * 添加任务
     *
     * @param jobInfoBo 任务信息
     * @return 添加结果
     */
    public boolean addJob(XxlJobInfoBo jobInfoBo) {
        String url = jobConfig.getAddresses() + JobConstant.Api.ADD;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, jobConfig.getAccessToken())
                .body(JSONUtil.toJsonStr(jobInfoBo))
                .timeout(jobConfig.getTimeout())
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
        String url = jobConfig.getAddresses() + JobConstant.Api.REMOVE;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, jobConfig.getAccessToken())
                .form("id", jobId)
                .timeout(jobConfig.getTimeout())
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
        String url = jobConfig.getAddresses() + JobConstant.Api.STOP;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, jobConfig.getAccessToken())
                .form("id", jobId)
                .timeout(jobConfig.getTimeout())
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
        String url = jobConfig.getAddresses() + JobConstant.Api.START;
        HttpResponse httpResponse = HttpRequest.post(url)
                .header(JobConstant.XXL_JOB_ACCESS_TOKEN, jobConfig.getAccessToken())
                .form("id", jobId)
                .timeout(jobConfig.getTimeout())
                .execute();
        if (!httpResponse.isOk()) {
            log.error("启动任务失败：{},jobId:{}", httpResponse.body(), jobId);
            return false;
        }
        return true;
    }

    public XxlJobUtil setJobConfig(String addresses, String accessToken) {
        this.jobConfig = new XxlJobConfig(addresses, accessToken);
        return this;
    }
}
