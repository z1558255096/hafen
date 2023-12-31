package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_web_recommend")
public class LessonWebRecommend implements Serializable {
    private static final long serialVersionUID = 795590960833946074L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer campusId;
    private String img;
    private String description;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;


}
