package com.moil.hafen.common.utils.lock;

import java.util.function.Supplier;


/**
 * 锁管理器
 *
 * @author zhouyok
 * @date 2023/12/09
 */
public interface LockManager {
    /**
     * 带返回值的分布式锁模版方法
     * (默认最长等待锁时间3秒,默认锁过期时间10秒)
     *
     * @param lockKey       锁key
     * @param serviceHandle 业务操作
     * @param <T>           返回值类型
     * @return 返回业务操作的返回值
     * //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    <T> T execute(String lockKey, Supplier<T> serviceHandle);

    /**
     * 带返回值的分布式锁模版方法
     *
     * @param lockKey       锁key
     * @param tryWaitTime   尝试获取锁最长等待时间(毫秒)
     * @param serviceHandle 业务处理
     * @param <T>           返回值类型
     * @return 返回业务操作的返回值
     * //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    <T> T execute(String lockKey, long tryWaitTime, Supplier<T> serviceHandle);

    /**
     * 带返回值的分布式锁模版方法
     *
     * @param lockKey       锁key
     * @param tryWaitTime   尝试获取锁最长等待时间(毫秒)
     * @param serviceHandle 业务处理
     * @param <T>           返回值类型
     * @param expireTime    锁过期时间(毫秒)
     * @return 返回业务操作的返回值
     * //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    <T> T execute(String lockKey, long tryWaitTime, long expireTime, Supplier<T> serviceHandle);

    /**
     * 分布式锁模版方法（无返回值）
     * (默认最长等待锁时间3秒,默认锁过期时间10秒)
     *
     * @param lockKey       锁key
     * @param serviceHandle 业务处理
     * @param serviceHandle 业务处理
     *                      //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    void executeNoReturn(String lockKey, LockServiceHandle serviceHandle);

    /**
     * 分布式锁模版方法（无返回值）
     *
     * @param lockKey       锁key
     * @param tryWaitTime   尝试获取锁最长等待时间(毫秒)
     * @param serviceHandle 业务处理
     *                      //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    void executeNoReturn(String lockKey, long tryWaitTime, LockServiceHandle serviceHandle);

    /**
     * 分布式锁模版方法（无返回值）
     *
     * @param lockKey       锁key
     * @param tryWaitTime   尝试获取锁最长等待时间(毫秒)
     * @param expireTime    锁过期时间(毫秒)
     * @param serviceHandle 业务处理
     *                      //     * @throws RedisTryLockException 开锁失败时,将抛出加锁失败异常
     */
    void executeNoReturn(String lockKey, long tryWaitTime, long expireTime, LockServiceHandle serviceHandle);
}
