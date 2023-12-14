package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_class_info")
@HeadStyle(fillForegroundColor=52)
@ExcelIgnoreUnannotated
@ApiModel(value = "班级信息")
public class ClassInfo implements Serializable {
    private static final long serialVersionUID = -221707941712925761L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "班级名称")
    @ApiModelProperty(value = "班级名称")
    private String name;
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    @ExcelProperty(value = "所属课程")
    @ApiModelProperty(value = "课程名称")
    private transient String lessonName;
    @ApiModelProperty(value = "校区id")
    private Integer campusId;
    @ExcelProperty(value = "所属校区")
    @ApiModelProperty(value = "所属校区")
    private transient String campusName;
    @ExcelProperty(value = "学员数")
    @ApiModelProperty(value = "学员数")
    private transient Integer studentCount;
    @ExcelProperty(value = "班主任")
    @ApiModelProperty(value = "班主任")
    private String classTeacher;
    private String teacher;
    private Double teachingCount;
    @ExcelProperty(value = "上课时间")
    private transient String classTime;
    private Integer type;//课程类型 1科技营 2体适能 3公社课程

    @ExcelProperty(value = "上课时间")
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
