package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_lesson_advance")
public class CommuneLessonAdvance implements Serializable {
    private static final long serialVersionUID = 3652725988022270653L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer type;//类型： 0单选，1多选，2文本输入
    private String title;
    private String option;
    private Integer index;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
