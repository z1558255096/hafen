package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    /**
     * 校区名称
     */
    @ApiModelProperty( "校区名称")
    @ExcelProperty(value = "校区名称")
    private String name;

    /**
     * 联系人
     */
    @ApiModelProperty( "联系电话")
    @ExcelProperty(value = "联系电话")
    private String telephone;

    /**
     * 省份
     */
    @ApiModelProperty( "省份")
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty( "城市")
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty( "区/县")
    private String district;

    /**
     * 详细地址
     */
    @ApiModelProperty( "详细地址")
    private String address;

    /**
     * 描述
     */
    @ApiModelProperty( "描述")
    private String description;

    /**
     * 业务范围：0-科技营、体适能；1-科技营；2-体适能
     */
    @ApiModelProperty( "业务范围：0-科技营、体适能；1-科技营；2-体适能")
    private Integer businessScope;

    /**
     * 校区标识
     */
    @ApiModelProperty( "校区标识")
    private String logo;

    /**
     * 校区状态：1-正常；0-停用
     */
    @ApiModelProperty("校区状态：1-正常；0-停用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty( "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty( "修改时间")
    private Date modifyTime;

    /**
     * 删除标识：0-正常；1-删除
     */
    @ApiModelProperty("删除标识：0-正常；1-删除")
    @TableLogic
    private Integer delFlag;

    private transient String createTimeFrom;
    private transient String createTimeTo;

    @ExcelProperty(value = "校区地址")
    private transient String fullAddress;

    @ApiModelProperty("部门列表")
    @TableField(exist = false)
    private List<Dept> children;

    public String getFullAddress() {
        return province.concat(city).concat(address);
    }
}
