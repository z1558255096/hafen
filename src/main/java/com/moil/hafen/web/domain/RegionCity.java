package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 陈子杰
 * @Description 市级信息表(RegionCity)实体类
 * @Version 1.0.0
 * @Date 2023-12-15 09:03:08
 */
@Data
@TableName("t_region_city")
public class RegionCity implements Serializable {
    private static final long serialVersionUID = -82732008326257632L;
    /**
     * 自增物理主键
     */
    private Integer id;
    /**
     * 国际编码
     */
    private String nativeCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 国家编码
     */
    private String countryCode;
    /**
     * 国家名称
     */
    private String countryName;
    /**
     * 省份编码
     */
    private String provinceCode;
    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 备注省份编码
     */
    private String remarkCode;
    /**
     * 地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县
     */
    private Integer regionLevel;
}

