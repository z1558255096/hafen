package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 校区表
 * @Version 1.0.0
 * @Date 2023/12/07 12:29
 */
@Data
@TableName("t_campus")
@ApiModel(description="管理校区")
@HeadStyle(fillForegroundColor=52)
@ExcelIgnoreUnannotated
public class Campus implements Serializable {
    private static final long serialVersionUID = 3665013544492472037L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(name = "校区名称")
    @ExcelProperty(value = "校区名称")
    private String name;

    @ApiModelProperty(name = "联系电话")
    @ExcelProperty(value = "联系电话")
    private String telephone;

    @ApiModelProperty(name = "省份")
    private String province;

    @ApiModelProperty(name = "城市")
    private String city;

    @ApiModelProperty(name = "区/县")
    private String district;

    @ApiModelProperty(name = "详细地址")
    private String address;

    @ApiModelProperty(name = "描述")
    private String description;

    @ApiModelProperty(name = "业务范围：0-科技营、体适能；1-科技营；2-体适能")
    private Integer businessScope;

    @ApiModelProperty(name = "校区标识")
    private String logo;

    @ApiModelProperty(name = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "修改时间")
    private Date modifyTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;

    @ExcelProperty(value = "校区地址")
    private transient String fullAddress;

    public String getFullAddress() {
        return province.concat(city).concat(address);
    }
}
