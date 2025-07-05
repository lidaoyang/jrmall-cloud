package com.jrmall.pilates.common.job.em;


import lombok.Getter;

/**
 * 触发器状态枚举
 */
@Getter
public enum TriggerStatusEnum {
    /**
     * 停止
     */
    STOPED(0, "停止"),

    /**
     * 运行
     */
    RUNNING(1, "运行");


    private final String title;
    private final int status;

    TriggerStatusEnum(int status, String title) {
        this.status = status;
        this.title = title;
    }

    public static TriggerStatusEnum match(String name, TriggerStatusEnum defaultItem) {
        for (TriggerStatusEnum item : TriggerStatusEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return defaultItem;
    }

}
