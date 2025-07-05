package com.jrmall.pilates.common.job.em;

import lombok.Getter;

/**
 * 调度类型枚举
 */
public enum ScheduleTypeEnum {

    NONE("无"),

    /**
     * schedule by cron
     */
    CRON("CRON"),

    /**
     * schedule by fixed rate (in seconds)
     */
    FIX_RATE("固定速度"),

    /**
     * schedule by fix delay (in seconds)， after the last time
     */
    /*FIX_DELAY("固定延迟")*/;

    @Getter
    private final String title;

    ScheduleTypeEnum(String title) {
        this.title = title;
    }

    public static ScheduleTypeEnum match(String name, ScheduleTypeEnum defaultItem){
        for (ScheduleTypeEnum item: ScheduleTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}
