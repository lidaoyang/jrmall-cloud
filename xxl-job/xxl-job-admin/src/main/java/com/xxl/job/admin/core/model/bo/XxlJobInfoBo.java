package com.xxl.job.admin.core.model.bo;

import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.scheduler.ScheduleTypeEnum;

import java.sql.Time;
import java.util.Date;

/**
 * 添加任务bo
 *
 * @author: Dao-yang.
 * @date: Created in 2025/6/30 10:57
 */
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
    private Integer alarmType = 0;

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
    private int triggerStatus = 1;

    /**
     * 下次调度时间
     */
    private long triggerNextTime;

    /**
     * 任务结束时间, 指定任务结束时间后,将会添加一个定时结束该任务的任务
     */
    private Date endTime;

    /**
     * 结束任务的执行器 handler, 指定任务结束时间后,该字段必传
     */
    private String endExecutorHandler;

    public int getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(int jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmUrl() {
        return alarmUrl;
    }

    public void setAlarmUrl(String alarmUrl) {
        this.alarmUrl = alarmUrl;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleConf() {
        return scheduleConf;
    }

    public void setScheduleConf(String scheduleConf) {
        this.scheduleConf = scheduleConf;
    }

    public String getExecutorHandler() {
        return executorHandler;
    }

    public void setExecutorHandler(String executorHandler) {
        this.executorHandler = executorHandler;
    }

    public String getExecutorParam() {
        return executorParam;
    }

    public void setExecutorParam(String executorParam) {
        this.executorParam = executorParam;
    }

    public Boolean getExecutorFailStop() {
        return executorFailStop;
    }

    public void setExecutorFailStop(Boolean executorFailStop) {
        this.executorFailStop = executorFailStop;
    }

    public int getTriggerStatus() {
        return triggerStatus;
    }

    public void setTriggerStatus(int triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    public long getTriggerNextTime() {
        return triggerNextTime;
    }

    public void setTriggerNextTime(long triggerNextTime) {
        this.triggerNextTime = triggerNextTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndExecutorHandler() {
        return endExecutorHandler;
    }

    public void setEndExecutorHandler(String endExecutorHandler) {
        this.endExecutorHandler = endExecutorHandler;
    }
}
