package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 校区部门表
 * @Version 1.0.0
 * @Date 2023/12/7 12:15
 */
@Data
@TableName("t_dept")
public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "所属校区ID")
    private Integer campusId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "是否默认部门")
    private Integer isDefault;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
}
