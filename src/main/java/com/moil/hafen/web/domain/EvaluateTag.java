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
 * @Description 课堂点评评价体系
 * @Version 1.0.0
 * @Date 2023/12/11 11:59
 */
@Data
@TableName("t_evaluate_tag")
@ApiModel("课堂点评评价体系")
public class EvaluateTag implements Serializable {

    private static final long serialVersionUID = 3526292856970448961L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("评分名称")
    private String name;
    @ApiModelProperty("评分类型：0-教师评分；1-家长评分")
    private Integer type;
    @ApiModelProperty("状态：0-上架;1-下架")
    private Integer state;
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
