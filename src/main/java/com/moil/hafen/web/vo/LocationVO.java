package com.moil.hafen.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 陈子杰
 * @Description 位置信息
 * @Version 1.0.0
 * @Date 2023/12/9 16:39
 */
@Data
@ApiModel("位置信息")
public class LocationVO {
    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("经度")
    private String lat;

    @ApiModelProperty("纬度")
    private String lng;
}
