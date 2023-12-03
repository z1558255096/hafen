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
    private String province;
    private String city;
    private String district;
    private String address;
    private String description;
    private String business;
    private String logo;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    @ExcelProperty(value = "校区地址")
    private transient String fullAddress;

    public String getFullAddress() {
        return province.concat(city).concat(address);
    }
}
