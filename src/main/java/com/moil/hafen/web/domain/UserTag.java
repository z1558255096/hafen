package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 用户标签管理
 * @Version 1.0.0
 * @Date 2023/12/10 11:46
 */
@Data
@TableName("t_user_tag")
@ApiModel("用户标签管理")
public class UserTag implements Serializable {
    private static final long serialVersionUID = 9124581496567814593L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("标签名称")
    private String name;

    private Date createTime;
    private Date modifyTime;
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
}
