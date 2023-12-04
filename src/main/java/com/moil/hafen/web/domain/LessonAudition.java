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
import java.util.List;

@Data
@TableName("t_lesson_audition")
@HeadStyle(fillForegroundColor=52)
@ExcelIgnoreUnannotated
@ApiModel(value = "试听课程")
public class LessonAudition implements Serializable {
    private static final long serialVersionUID = 1719983841769611114L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "上课日期")
    private String classDate;
    @ExcelProperty(value = "上课时间")
    private String classTime;
    @ApiModelProperty(value = "上课开始时间")
    private String classStartTime;
    @ApiModelProperty(value = "上课结束时间")
    private String classEndTime;
    private Date classDateTime;
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    @ExcelProperty(value = "课程名称")
    private transient String lessonName;
    @ApiModelProperty(value = "课程分类")
    private transient String categoryName;
    @ExcelProperty(value = "上课教师")
    private String teacher;
    @ApiModelProperty(value = "教师id")
    private Integer staffId;
    @ExcelProperty(value = "上课学员数量")
    private transient Integer studentCount;
    @ExcelProperty(value = "预约状态 0预约中 未达到上课开始时间   1预约结束 达到上课开始时间")
    private transient String reservationStatus;//预约状态 0预约中 未达到上课开始时间   1预约结束 达到上课开始时间

    private Integer campusId;
    @ExcelProperty(value = "所属校区")
    private transient String campusName;

    private Integer attendStudentCount;

    private String cover;
    private Integer status;//试听状态 0 上架 1下架
    private Integer type;//课程类型 1科技营 2体适能 3公社课程

    private transient int studentReservationStatus;//学生预约状态 0未预约 1已预约
    private transient Integer classStatus;//到课状态 0未到课 1到课

    @ExcelProperty(value = "创建时间")
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    @ExcelProperty(value = "试听状态")
    private transient String statusStr;
    private transient List<String> classDateList;

    public String getStatusStr() {
        if (status == null) {
            return null;
        }
        if (status == 0) {
            return "上架";
        } else {
            return "下架";
        }
    }
    public String getReservationStatus() {
        if((new Date()).before(classDateTime)){
            return "预约中";
        }else{
            return "预约结束";
        }
    }
}
