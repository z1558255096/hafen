package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_evaluate_tag")
@ApiModel("课堂点评评价体系")
public class EvaluateTag implements Serializable {

    private static final long serialVersionUID = 3526292856970448961L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer type;
    private Integer state;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
