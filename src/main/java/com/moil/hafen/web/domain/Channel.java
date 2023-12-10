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
 * @Description 渠道管理
 * @Version 1.0.0
 * @Date 2023/12/10 11:33
 */
@Data
@TableName("t_channel")
@ApiModel("渠道管理")
public class Channel implements Serializable {
    private static final long serialVersionUID = 3915365477392868018L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("渠道名称")
    private String name;
    @ApiModelProperty("创建时间")
    private Date createTime;
    private Date modifyTime;
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
