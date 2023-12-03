package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_customer_behavior")
public class CustomerBehavior implements Serializable {
    private static final long serialVersionUID = -6045416133774092734L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer optionId;
    private Integer customerId;
    private String behavior;//类型  like 点赞  collect 收藏
    private Integer source;//数据来源1文章 2课程 3门票 4商品
    private Integer type;//类型 1线上课程 2线下课程

    private transient String cover;
    private transient String title;

    private Date createTime;

    public CustomerBehavior(Integer optionId, Integer customerId, String behavior, Integer source, Date createTime) {
        this.optionId = optionId;
        this.customerId = customerId;
        this.behavior = behavior;
        this.source = source;
        this.createTime = createTime;
    }
}
