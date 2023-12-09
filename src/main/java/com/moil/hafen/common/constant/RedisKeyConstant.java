package com.moil.hafen.common.constant;

/**
 * Redis Key 定义常量类
 *
 * @author SongZichen
 * @since 2023年12月09日 01:32:12
 **/
public class RedisKeyConstant {
    /**
     * 公社课程订单
     */
    public static String COMMUNE_LESSON_ORDER(String customerId) {
        return String.join(":", "hafen", "commune_lesson_order_lock_key", customerId);
    }

}
