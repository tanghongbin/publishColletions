package com.binding.newbindview;

public enum  BindViewNotifyStatus {
    /**
     * 通知所有item变化
     */
    ALL_CHANGE(1),
    /**
     * 通知单个item变化
     */
    ITEM_CHANGE(2),
    /**
     * 通知单个item插入
     */
    ITEM_INSERT(3),
    /**
     * 通知单个item移动
     */
    ITEN_MOVE(4),
    /**
     * 通知单个item移除
     */
    ITEM_REMOVE(5),
    /**
     * 通知一段范围内的数据变化
     */
    RANGE_CHANGE(6),
    /**
     * 通知一段范围内的数据插入
     */
    RANGE_INSERT(7),

    /**
     * 通知一段范围内的数据移除
     */
    RANGE_REMOVE(8)
    ;
    private int value;

    BindViewNotifyStatus(int value) {
        this.value = value;
    }

}
