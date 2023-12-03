package com.moil.hafen.web.domain;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-13 09:58
 **/
@Data
@TableName("t_customer")
@HeadStyle(fillForegroundColor=52)
@ExcelIgnoreUnannotated
public class Customer implements Serializable {
    private static final long serialVersionUID = 7568713665947096812L;
    @ExcelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "用户昵称")
    private String nickName;
    private String avatar;

    @ExcelProperty(value = "手机号")
    private String mobile;

    @ExcelProperty(value = "注册来源")
    private String source;

    @ExcelProperty(value = "用户标签")
    private String tag;

    @ExcelProperty(value = "是否学员")
    private Integer isStudent;//是否学员 0非学员 1是学员
    private Integer parentId;
    private String openId;
    private String unionId;


    @ExcelProperty(value = "注册时间")
    private Date createTime;
    private Date modifyTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient int hafenCoin;
    private transient int taskCount;
}
