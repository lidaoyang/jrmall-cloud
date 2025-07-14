package com.jrmall.cloud.common.job.model;

import com.jrmall.cloud.common.job.em.AlarmTypeEnum;
import com.jrmall.cloud.common.job.em.ScheduleTypeEnum;
import com.jrmall.cloud.common.job.em.TriggerStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * xxl-job info
 */
@Data
public class XxlJobInfoBo {

    /**
     * 执行器主键ID
     */
    private int jobGroup;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 报警类型(0:不报警;1:邮件;2:企业微信;3:飞书;4:钉钉;5:webhook)
     */
    private Integer alarmType = AlarmTypeEnum.NOT.getAlarmType();

    /**
     * 报警URL
     */
    private String alarmUrl;

    /**
     * 调度类型
     */
    private String scheduleType = ScheduleTypeEnum.NONE.name();

    /**
     * 调度配置，值含义取决于调度类型
     */
    private String scheduleConf;

    /**
     * 执行器，任务Handler
     */
    private String executorHandler;

    /**
     * 执行器，任务参数
     */
    private String executorParam;

    /**
     * 执行失败后是否停止(默认true)
     */
    private Boolean executorFailStop;

    /**
     * 调度状态：0-停止，1-运行
     */
    private int triggerStatus = TriggerStatusEnum.RUNNING.getStatus();

    /**
     * 下次调度时间
     */
    private long triggerNextTime;

    /**
     * 任务结束时间, 指定任务结束时间后,将会添加一个定时结束该任务的任务
     */
    private Date endTime;

}

