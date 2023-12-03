package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: sojourn
 * @description:
 * @author: Moil
 * @create: 2022-06-16 19:55
 **/
@Data
@TableName("t_sys_region")
public class Region implements Serializable {
    private static final long serialVersionUID = -5827646070661017249L;
    @TableId(value = "id")
    private int id;
    private int parent;
    private String name;


}
