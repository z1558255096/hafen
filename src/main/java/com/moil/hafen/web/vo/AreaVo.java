package com.moil.hafen.web.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AreaVo implements Serializable {
    private static final long serialVersionUID = -8417935491174096625L;
    private String province;
    private String city;
    private String district;

    public AreaVo(String province, String city, String district) {
        this.province = province;
        this.city = city;
        this.district = district;
    }
}
