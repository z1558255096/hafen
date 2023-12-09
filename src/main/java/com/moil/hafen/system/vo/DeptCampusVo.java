package com.moil.hafen.system.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author 陈子杰
 * @Description 用户部门校区关联表
 * @Version 1.0.0
 * @Date 2023/12/9 21:11
 */
@Data
@ApiModel("用户部门校区关联表")
public class DeptCampusVo {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 校区id
     */
    private Integer campusId;

    /**
     * 校区名称
     */
    private String campusName;
}
