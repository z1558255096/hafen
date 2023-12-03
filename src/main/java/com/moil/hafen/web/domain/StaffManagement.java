package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_staff_management")
public class StaffManagement implements Serializable {
    private static final long serialVersionUID = 1512395421985474154L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String mobile;
    private Integer campusId;
    private Integer loginType;
    private String account;
    private String password;
    private Integer status;//0在校；1离职

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
