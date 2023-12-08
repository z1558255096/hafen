package com.moil.hafen.common.utils.lock.exception;


/**
 * 锁定异常
 *
 * @author zhouyok
 * @date 2023/12/09
 */
public class LockException extends RuntimeException {


    private static final long serialVersionUID = 4724305095374026114L;

    /**
     * Default constructor
     */
    public LockException() {
        super();
    }

    /**
     * Constructor with message & cause
     *
     * @param message
     * @param cause
     */
    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message
     *
     * @param message
     */
    public LockException(String message) {
        super(message);
    }

    /**
     * Constructor with message format
     *
     * @param msgFormat
     * @param args
     */
    public LockException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }

    /**
     * Constructor with cause
     *
     * @param cause
     */
    public LockException(Throwable cause) {
        super(cause);
    }
}
