package com.moil.hafen.web.service;

import com.moil.hafen.web.domain.RegionCity;
import com.moil.hafen.web.domain.RegionDistrict;
import com.moil.hafen.web.domain.RegionProvince;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 省市区业务层
 * @Version 1.0.0
 * @Date 2023/12/15 9:06
 */
public interface RegionService {
    /**
     * 获取省份信息
     *
     * @return {@link List}<{@link RegionProvince}>
     */
    List<RegionProvince> getRegion();

    /**
     * 获取城市信息
     *
     * @param provinceCode 省/自治区代码
     * @return {@link List}<{@link RegionCity}>
     */
    List<RegionCity> getCity(String provinceCode);

    /**
     * 获取区县信息
     *
     * @param cityCode 城市代码
     * @return {@link List}<{@link RegionDistrict}>
     */
    List<RegionDistrict> getDistrict(String cityCode);
}
