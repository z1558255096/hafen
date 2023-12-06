package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 登录日志实体类
 * @Version 1.0.0
 * @Date 2023/12/06 09:03
 */
@TableName("t_login_log")
@Data
public class LoginLog {
    /**
     * 用户 ID
     */
    private String username;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录地点
     */
    private String location;

    /**
     * ip
     */
    private String ip;
}
