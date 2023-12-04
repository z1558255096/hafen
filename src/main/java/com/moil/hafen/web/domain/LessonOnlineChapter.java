package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_online_chapter")
@ApiModel(value = "线上课程章节管理")
public class LessonOnlineChapter implements Serializable {
    private static final long serialVersionUID = 7635102984092153161L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    @ApiModelProperty(value = "章节名称")
    private String name;
    @ApiModelProperty(value = "允许试听：0否  1是")
    private Integer auditionStatus;//允许试听：0否  1是
    @ApiModelProperty(value = "状态：0上架 1下架")
    private Integer status;//0上架 1下架
    private Double duration;
    @ApiModelProperty(value = "资源类型 1视频 2音频")
    private Integer type;//资源类型 1视频 2音频
    @ApiModelProperty(value = "资源地址")
    private String url;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除


    private Date createTime;
    private Date modifyTime;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient String typeName;
    private transient String auditionStatusStr;
    private transient String statusStr;
    @ApiModelProperty(value = "上次学习标识 0否 1是")
    private transient int studyStatus;//上次学习标识 0否 1是


    public String getTypeName() {
        if(type == null){
            return null;
        }
        if(type == 1){
            return "视频";
        }else {
            return "音频";
        }
    }

    public String getAuditionStatusStr() {
        if(auditionStatus == null){
            return null;
        }
        if(auditionStatus==0){
            return "否";
        }else {
            return "是";
        }
    }

    public String getStatusStr() {
        if(status == null){
            return null;
        }
        if(status==0){
            return "上架";
        }else {
            return "下架";
        }
    }
}
