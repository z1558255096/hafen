package com.moil.hafen.common.utils.lock;

import cn.hutool.core.date.SystemClock;

import com.moil.hafen.common.utils.LogUtil;
import com.moil.hafen.common.utils.lock.exception.RedisTryLockException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


/**
 * redis锁管理器impl
 *
 * @author zhouyok
 * @date 2023/12/09
 */
@Slf4j
public class RedisLockManagerImpl implements LockManager {

    /**
     * 默认等待锁时间(毫秒)
     */
    private final static long DEFAULT_TRY_WAIT_TIME = 3 * 1000L;
    /**
     * 默认redis锁过期时间(毫秒)
     */
    private final static long DEFAULT_EXPIRE_TIME = 10 * 1000L;
    /**
     * redis连接对象
     */
    private RedissonClient redissonClient;

    @Override
    public <T> T execute(String lockKey, Supplier<T> serviceHandle) {
        return execute(lockKey, DEFAULT_TRY_WAIT_TIME, DEFAULT_EXPIRE_TIME, serviceHandle);
    }

    @Override
    public <T> T execute(String lockKey, long tryWaitTime, Supplier<T> serviceHandle) {
        return execute(lockKey, tryWaitTime, DEFAULT_EXPIRE_TIME, serviceHandle);
    }

    @Override
    public <T> T execute(String lockKey, long tryWaitTime, long expireTime, Supplier<T> serviceHandle) {
        long currentTime = SystemClock.now();
        RLock lock = null;
        T result;
        try {
            lock = trylock(lockKey, tryWaitTime, expireTime);
            // 业务处理
            result = serviceHandle.get();
        } catch (Exception e) {
            LogUtil.error(log, "execute >> 【redis分布式锁服务】业务操作出现异常！lockKey = {}", e, lockKey);
            throw e;
        } finally {
            // 解锁
            unlock(lock, lockKey, currentTime);
        }
        return result;
    }

    @Override
    public void executeNoReturn(String lockKey, LockServiceHandle serviceHandle) {
        executeNoReturn(lockKey, DEFAULT_TRY_WAIT_TIME, DEFAULT_EXPIRE_TIME, serviceHandle);
    }

    @Override
    public void executeNoReturn(String lockKey, long tryWaitTime, LockServiceHandle serviceHandle) {
        executeNoReturn(lockKey, tryWaitTime, DEFAULT_EXPIRE_TIME, serviceHandle);
    }

    @Override
    public void executeNoReturn(String lockKey, long tryWaitTime, long expireTime, LockServiceHandle serviceHandle) {
        long currentTime = SystemClock.now();
        RLock lock = null;
        try {
            lock = trylock(lockKey, tryWaitTime, expireTime);
            // 业务处理
            serviceHandle.handle();
        } catch (Exception e) {
            LogUtil.error(log, "executeNoReturn >> 【redis分布式锁服务】业务操作出现异常！lockKey = {}", e, lockKey);
            throw e;
        } finally {
            // 解锁
            unlock(lock, lockKey, currentTime);
        }
    }

    /**
     * reids锁 - 加锁
     *
     * @param lockKey
     * @param tryWaitTime
     * @param expireTime
     */
    private RLock trylock(String lockKey, long tryWaitTime, long expireTime) {

        RLock lock;
        try {
            lock = redissonClient.getLock(lockKey);
            boolean isSuccess = lock.tryLock(tryWaitTime, expireTime, TimeUnit.MILLISECONDS);
            if (!isSuccess) {
                LogUtil.info(log, "【redis分布式锁服务】trylock-加锁失败,锁被其他线程占用！lockKey = {}", lockKey);
                throw new RedisTryLockException();
            }
            LogUtil.info(log, "【redis分布式锁服务】加锁成功！lockKey = {}", lockKey);
        } catch (Exception e) {
            LogUtil.error(log, "【redis分布式锁服务】trylock-加锁失败！lockKey = {}", e, lockKey);
            throw new RedisTryLockException("redis加锁失败", e);
        }
        return lock;
    }

    /**
     * reids锁 - 解锁
     *
     * @param lock
     */
    private boolean unlock(RLock lock, String lockKey, long lockStartTime) {
        if (null == lock) {
            return false;
        }
        try {
            lock.unlock();
            LogUtil.info(log, "【redis分布式锁服务】解锁成功！lockKey = {},锁持续时间={}ms", lockKey, SystemClock.now() - lockStartTime);
        } catch (Exception e) {
            LogUtil.error(log, "【redis分布式锁服务】unlock-解锁失败！lockKey = {}", e, lockKey);
            return true;
        }
        return true;
    }


    /**
     * Setter method for property <tt>redisCache</tt>.
     *
     * @param redissonClient value to be assigned to property redisCache
     */
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
