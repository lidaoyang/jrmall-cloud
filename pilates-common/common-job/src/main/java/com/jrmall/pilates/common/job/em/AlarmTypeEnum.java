package com.jrmall.pilates.common.job.em;


import lombok.Getter;

/**
 * 告警类型
 */
@Getter
public enum AlarmTypeEnum {

    /**
     * 不报警
     */
    NOT(0, "不报警"),

    /**
     * 邮件
     */
    EMAIL(1, "邮件告警"),


    /**
     * 企业微信
     */
    ENT_WECHAT(2, "企业微信告警"),

    /**
     * 飞书
     */
    FEI_SHU(3, "飞书告警"),

    /**
     * 钉钉
     */
    DING_DING(4, "钉钉告警"),

    /**
     * webhook
     */
    WEBHOOK(5, "Webhook告警");


    private final String title;
    private final int alarmType;

    AlarmTypeEnum(int alarmType, String title) {
        this.alarmType = alarmType;
        this.title = title;
    }

    public static AlarmTypeEnum match(String name, AlarmTypeEnum defaultItem) {
        for (AlarmTypeEnum item : AlarmTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}
