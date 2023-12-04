package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_student")
@ApiModel(value = "学生")
public class Student implements Serializable {
    private static final long serialVersionUID = 4595146893031552786L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private String studentName;
    private String sex;
    private Date birthday;
    private String mobile;
    private String mobileOwner;
    private Integer activeStatus;//激活状态 0未激活 1激活
    private Integer delFlag;

    private Date createTime;
    private Date modifyTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient Date myCreateTime;
    private transient Date cancelTime;

}
