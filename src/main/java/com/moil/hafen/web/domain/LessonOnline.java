package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_online")
@HeadStyle(fillForegroundColor=52)
@ExcelIgnoreUnannotated
@ApiModel(value = "线上课程管理")
public class LessonOnline implements Serializable {
    private static final long serialVersionUID = 8704438083236958889L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "课程名称")
    private String name;
    @ExcelProperty(value = "小节课程")
    private transient Integer chapterCount;
    @ExcelProperty(value = "课程类别")
    private transient String categoryName;
    private Integer categoryId;
    private Integer item;//课程类型 0付费;1免费
    @ExcelProperty(value = "原价")
    private Double price;
    @ExcelProperty(value = "优惠价")
    private Double actualPrice;
    @ExcelProperty(value = "课程状态")
    private transient String statusStr;
    @ExcelProperty(value = "上架推荐区")
    private transient String recommendStatusStr;
    @ExcelProperty(value = "上架微官网")
    private transient String websiteStatusStr;
    private Integer recommendStatus;//上架推荐区 0否 1是
    private Integer websiteStatus;//上架微官网 0否 1是
    private String cover;
    private String tag;
    private String description;
    private String detail;
    private Integer delFlag;//0正常 1删除
    private Integer status;//0上架 1下架
    private Integer type;//课程类型 1科技营 2体适能

    private Date createTime;
    @ExcelProperty(value = "更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient int evaluateCount;

    public String getRecommendStatusStr() {
        if(recommendStatus ==null){
            return null;
        }
        if(recommendStatus==0){
            return "否";
        }else {
            return "是";
        }
    }

    public String getWebsiteStatusStr() {
        if(websiteStatus ==null){
            return null;
        }
        if(websiteStatus==0){
            return "否";
        }else {
            return "是";
        }
    }

    public String getStatusStr() {
        if(status ==null){
            return null;
        }
        if(status==0){
            return "上架";
        }else {
            return "下架";
        }
    }
}
