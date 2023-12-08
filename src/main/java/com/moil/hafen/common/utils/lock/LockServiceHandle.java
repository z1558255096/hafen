package com.moil.hafen.common.utils.lock;

/**
 * 事务-业务逻辑接口
 *
 * @author Liluqing
 * @version DefaultHandle.java, v 0.1 2018-10-22 14:09
 */
@FunctionalInterface
public interface LockServiceHandle {

    /**
     * 业务处理
     *
     * @return
     */
    void handle();
}
