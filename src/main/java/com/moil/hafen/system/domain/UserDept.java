package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 陈子杰
 * @Description 用户部门关联表
 * @Version 1.0.0
 * @Date 2023/12/9 20:47
 */
@Data
@TableName("t_user_dept")
public class UserDept {
    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("部门id")
    private Integer deptId;
}
