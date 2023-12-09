package com.moil.hafen.third;

import com.moil.hafen.third.resp.TencentResp;

/**
 * @Author 陈子杰
 * @Description 腾讯地图服务
 * @Version 1.0.0
 * @Date 2023/12/9 18:04
 */
public interface TencentService {
    /**
     * 根据关键字获取提示
     *
     * @param keyword 关键字
     * @param region  区域
     * @return {@link TencentResp}
     */
    TencentResp suggestion(String keyword, String region);

    /**
     * 根据经纬度获取位置信息
     *
     * @param lat 维度
     * @param lng 精度
     * @return {@link TencentResp}
     */
    TencentResp location(String lat, String lng);
}
