package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_online_study")
public class LessonOnlineStudy implements Serializable {
    private static final long serialVersionUID = 7875480502847103297L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer chapterId;
    private long duration;
    private long currentDuration;
    private Integer customerId;
    private Integer currentChapter;//当前学习的章节 0否 1是

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
