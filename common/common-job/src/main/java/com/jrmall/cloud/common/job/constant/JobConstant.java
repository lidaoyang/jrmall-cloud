package com.jrmall.cloud.common.job.constant;

/**
 * 任务调度常量
 *
 * @author: Dao-yang.
 * @date: Created in 2025/7/4 13:49
 */
public interface JobConstant {

    String XXL_JOB_ACCESS_TOKEN = "XXL-JOB-ACCESS-TOKEN";

    interface Api {
        /**
         * 添加任务
         */
        String ADD = "/api/job/add";

        /**
         * 移除任务
         */
        String REMOVE = "/api/job/remove";

        /**
         * 暂停任务
         */
        String STOP = "/api/job/stop";

        /**
         * 启动任务
         */
        String START = "/api/job/start";
    }
}
