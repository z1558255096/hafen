package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_coupon")
public class Coupon implements Serializable {
    private static final long serialVersionUID = -8270317021753608891L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Double usePrice;
    private Double discountPrice;
    private String cover;
    private Integer status;//状态 0上架 1下架
    private Date startTime;
    private Date endTime;
    private Integer target;//发放对象 0全部 1指定用户
    private transient List<Integer> customerIds;
    private String useBus;//可用业务 101:科技营-在线课程;102:科技营-线下课程;201:体适能-在线课程;202:体适能-线下课程;301:公社-课程;302:公社-门票
    private String remark;
    private Integer delFlag;//0正常 1删除
    private transient Integer customerStatus;//使用状态 0未使用 1已使用 2已过期
    private transient Date useTime;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient int issueCount;
    private transient int useCount;
}
