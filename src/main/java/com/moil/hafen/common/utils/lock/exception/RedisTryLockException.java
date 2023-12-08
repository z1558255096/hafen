/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.moil.hafen.common.utils.lock.exception;


/**
 * redis尝试锁定异常
 *
 * @author zhouyok
 * @date 2023/12/09
 */
public class RedisTryLockException extends LockException {


    private static final long serialVersionUID = -5740691674643051630L;

    /**
     * Default constructor
     */
    public RedisTryLockException() {
        super();
    }

    /**
     * Constructor with message & cause
     *
     * @param message
     * @param cause
     */
    public RedisTryLockException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message
     *
     * @param message
     */
    public RedisTryLockException(String message) {
        super(message);
    }

    /**
     * Constructor with message format
     *
     * @param msgFormat
     * @param args
     */
    public RedisTryLockException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     *
     * @param cause
     */
    public RedisTryLockException(Throwable cause) {
        super(cause);
    }
}
