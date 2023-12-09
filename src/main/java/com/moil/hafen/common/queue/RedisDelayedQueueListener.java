package com.moil.hafen.common.queue;

/**
 * @author SongZichen
 * @since 2023年12月09日 14:25:11
 **/
public interface RedisDelayedQueueListener<T> {

    /**
     * 执行方法
     * @param t
     */
    void invoke(T t);
}
