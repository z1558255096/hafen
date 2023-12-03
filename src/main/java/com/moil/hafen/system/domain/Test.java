package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_test")
public class Test {
    private Long id;
    private String field1;

    private Integer field2;

    private String field3;

    private Date createTime;
}