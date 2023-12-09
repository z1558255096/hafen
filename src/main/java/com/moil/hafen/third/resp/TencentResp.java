package com.moil.hafen.third.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 陈子杰
 * @Description 腾讯地图API返回结果
 * @Version 1.0.0
 * @Date 2023/12/9 18:07
 */
@Data
public class TencentResp {
    @ApiModelProperty("状态码")
    private Integer status;

    @ApiModelProperty("提示信息")
    private String message;

    @ApiModelProperty("总条数")
    private Integer count;

    @ApiModelProperty("数据")
    private String data;

    @ApiModelProperty("请求对象")
    private String requestId;
}
