package com.android_base.core.common.exception;

/**
 * 抛出现阶段不支持的功能的异常
 */
public class OperateNotSupportException extends RuntimeException {
    /**
     * Constructs a new {@code RuntimeException} that includes the current stack
     * trace.
     */
    public OperateNotSupportException() {
    }

    /**
     * Constructs a new {@code RuntimeException} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public OperateNotSupportException(String detailMessage) {
        super(detailMessage);
    }
}
