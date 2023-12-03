package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_user_tag")
@ApiModel("用户标签管理")
public class UserTag implements Serializable {
    private static final long serialVersionUID = 9124581496567814593L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
