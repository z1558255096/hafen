package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_class_student")
public class ClassStudent implements Serializable {
    private static final long serialVersionUID = 9190425453162509703L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private Integer studentId;
    private Integer customerId;
    private transient String studentName;
    private transient String sex;
    private transient String mobile;
    private Integer attendCount;
    private Integer remainderCount;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
