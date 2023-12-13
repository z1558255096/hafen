package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description (Clue)实体类
 * @Version 1.0.0
 * @Date 2023-12-14 02:35:11
 */
@Data
@TableName("t_clue")
public class Clue implements Serializable {
    private static final long serialVersionUID = -17391961186477732L;
    /**
     * 线索ID
     */
    @TableId(value = "id" ,type = IdType.AUTO)
    private Integer id;
    /**
     * 线索姓名
     */
    @NotNull(message = "线索姓名不能为空")
    private String clueName;
    /**
     * 联系电话
     */
    @NotNull(message = "联系电话不能为空")
    private String contactNumber;
    /**
     * 性别：0-男；1-女
     */
    private Integer gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 学院备注
     */
    private String collegeRemark;
    /**
     * 业务：1-科技营；2-体适能
     */
    @NotNull(message = "业务不能为空")
    private Integer business;
    /**
     * 咨询课程
     */
    @NotNull(message = "咨询课程不能为空")
    private Integer consultationCourse;
    /**
     * 渠道来源
     */
    private Integer channelId;
    /**
     * 归属校区
     */
    private Integer campusAffiliation;
    /**
     * 归属人员
     */
    private Integer personnelAffiliation;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建时间开始
     */
    @TableField(exist = false)
    private Date createTimeStart;
    /**
     * 创建时间结束
     */
    @TableField(exist = false)
    private Date createTimeEnd;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private String modifier;

    /**
     * 跟进状态：1-待跟进;2-跟进中;3-已到访;4-已成交;5-已失效
     */
    @TableField(exist = false)
    private String followUpStatus;

    /**
     * 下次跟进时间
     */
    @TableField(exist = false)
    private Date nextFollowUpTime;

    /**
     * 下次跟进时间开始
     */
    @TableField(exist = false)
    private Date nextFollowUpTimeStart;

    /**
     * 下次跟进时间结束
     */
    @TableField(exist = false)
    private Date nextFollowUpTimeEnd;

    /**
     * 线索姓名/手机号
     */
    @TableField(exist = false)
    private String keyword;
}

