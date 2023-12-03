package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-15 19:50
 **/
@Data
@TableName("t_customer_third")
public class CustomerThird implements Serializable {
    private static final long serialVersionUID = 4010819241748948019L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private String unionId;

    private String openId;

    private String nickName;

    private String avatar;

    private Integer roleType;

    private Date createTime;
    private Date modifyTime;

    public CustomerThird() {
    }

    public CustomerThird(String unionId, String openId, Date createTime,String nickName, String avatar,Integer customerId, Integer roleType) {
        this.unionId = unionId;
        this.openId = openId;
        this.createTime = createTime;
        this.nickName = nickName;
        this.avatar = avatar;
        this.customerId = customerId;
        this.roleType = roleType;
    }
}
