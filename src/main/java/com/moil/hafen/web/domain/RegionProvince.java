package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author 陈子杰
 * @Description 省份信息表(RegionProvince)实体类
 * @Version 1.0.0
 * @Date 2023-12-15 09:03:09
 */
@Data
@TableName("t_region_province")
public class RegionProvince implements Serializable {
    private static final long serialVersionUID = 653039419219045693L;
    /**
     * 自增物理主键
     */
    private Integer id;
    /**
     * 国际编码
     */
    private String nativeCode;
    /**
     * 省名称
     */
    private String provinceName;
    /**
     * 国家编码
     */
    private String countryCode;
    /**
     * 国家名称
     */
    private String countryName;
    /**
     * 备注省份编码
     */
    private String remarkCode;
    /**
     * 地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县
     */
    private Integer regionLevel;
}

