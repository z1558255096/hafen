package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = -1714476694755654924L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;

    @Size(max = 50, message = "{noMoreThan}")
    private String remark;

    private Date createTime;

    private Date modifyTime;

    private String status;

    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient String menuId;

}