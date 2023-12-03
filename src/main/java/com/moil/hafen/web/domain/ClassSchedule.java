package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_class_schedule")
public class ClassSchedule implements Serializable {
    private static final long serialVersionUID = 7775276661496570814L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer classId;
    private Integer rulesType;//重复规则 0不重复 1每周重复
    private String classTime;
    private Date startTime;
    private Date endTime;
    private Integer staffId;
    private String remark;
    private Integer status;//状态：0正常、1停课
    private Date classDate;
    private String weekDay;
    private Integer studentCount;
    private Integer attendStudentCount;
    private transient List<String> classDateList;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
