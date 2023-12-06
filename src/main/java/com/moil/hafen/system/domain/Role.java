package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 角色实体类
 * @Version 1.0.0
 * @Date 2023/12/6 14:48
 */
@Data
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -1714476694755654924L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    @ApiModelProperty("角色名称")
    private String roleName;

    @Size(max = 50, message = "{noMoreThan}")
    @ApiModelProperty("角色描述")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    private Date modifyTime;

    @ApiModelProperty("角色状态")
    private String status;

    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient String menuId;

}