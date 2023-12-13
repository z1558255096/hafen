package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 线索跟进记录
 * @Version 1.0.0
 * @Date 2023/12/14 3:09
 */
@Data
@ApiModel("线索跟进记录")
@TableName("t_clue_trail")
public class ClueTrail {
    /**
     * 跟进记录ID
     */
    private int id;

    /**
     * 线索ID
     */
    private int clueId;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 意向度：1-未知;2-低;3-中;4-高
     */
    private int intentionDegree;

    /**
     * 跟进方式：0-无;1-电话;2-面谈;3-微信;4-其他
     */
    private int followUpMethod;

    /**
     * 跟进状态：1-待跟进;2-跟进中;3-已到访;4-已成交;5-已失效
     */
    private String followUpStatus;

    /**
     * 下次跟进时间
     */
    private Date nextFollowUpTime;

    /**
     * 成交时间
     */
    private Date dealTime;

    /**
     * 成交课程
     */
    private String dealCourse;

    /**
     * 成交金额
     */
    private double dealAmount;

    /**
     * 成交课时
     */
    private int dealHours;

    /**
     * 成交人
     */
    private String dealPerson;

    /**
     * 创建时间
     */
    private Date createTime;

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
}
