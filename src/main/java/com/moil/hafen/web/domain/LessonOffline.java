package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_Lesson_Offline")
@ApiModel("线下课程")
public class LessonOffline implements Serializable {
    private static final long serialVersionUID = 8437259452992093235L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "课程名称")
    private String name;
    @ExcelProperty(value = "课程类别")
    private transient String categoryName;
    @ExcelProperty(value = "报价单数量")
    private transient Integer chapterCount;
    @ExcelProperty(value = "课程状态")
    private transient String statusStr;
    @ExcelProperty(value = "上架推荐区")
    private transient String recommendStatusStr;
    @ExcelProperty(value = "上架微官网")
    private transient String websiteStatusStr;
    @ApiModelProperty(value = "类别Id")
    private Integer categoryId;
    @ApiModelProperty(value = "上架推荐区 0否 1是")
    private Integer recommendStatus;//上架推荐区 0否 1是
    @ApiModelProperty(value = "上架微官网 0否 1是")
    private Integer websiteStatus;//上架推荐区 0否 1是
    @ApiModelProperty(value = "课程封面")
    private String cover;
    @ApiModelProperty(value = "课程标签")
    private String tag;
    @ApiModelProperty(value = "课程简介")
    private String description;
    @ApiModelProperty(value = "课程详情")
    private String detail;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty(value = "状态 0上架 1下架")
    private Integer status;//0上架 1下架
    @ApiModelProperty(value = "课程类型 1科技营 2体适能")
    private Integer type;//课程类型 1科技营 2体适能
    @ExcelProperty(value = "所属校区")
    private transient String campus;
    @ApiModelProperty(value = "开课校区 0全部校区 1指定校区")
    private Integer campusType;//开课校区 0全部校区 1指定校区
    private Date createTime;
    @ExcelProperty(value = "更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    @ApiModelProperty(value = "校区列表")
    private transient List<Integer> campusList;
    @ApiModelProperty(value = "课程校园价目表")
    private transient List<LessonCampusPrice> lessonCampusPriceList;
    @ApiModelProperty("价格")
    private transient Double price;
    @ApiModelProperty("实际价格")
    private transient Double actualPrice;

    public String getRecommendStatusStr() {
        if (recommendStatus == null) {
            return null;
        }
        if (recommendStatus == 0) {
            return "否";
        } else {
            return "是";
        }
    }

    public String getWebsiteStatusStr() {
        if (websiteStatus == null) {
            return null;
        }
        if (websiteStatus == 0) {
            return "否";
        } else {
            return "是";
        }
    }

    public String getStatusStr() {
        if (status == null) {
            return null;
        }
        if (status == 0) {
            return "上架";
        } else {
            return "下架";
        }
    }
}
