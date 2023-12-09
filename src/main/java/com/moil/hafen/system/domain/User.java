package com.moil.hafen.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 用户实体类
 * @Version 1.0.0
 * @Date 2023/12/06 09:08
 */
@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = -4852732617765810959L;
    /**
     * 账户状态
     */
    public static final String STATUS_VALID = "1";

    public static final String STATUS_LOCK = "0";

    // 默认密码
    public static final String DEFAULT_PASSWORD = "888888";

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickName;

    /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String workNum;

    @ApiModelProperty("生日")
    private String birthday;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 状态
     */
    @ApiModelProperty("状态：1-正常；0-锁定")
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;


    /**
     * 角色ID
     */
    @TableField(exist = false)
    private List<Integer> roleIds;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 部门ID
     */
    @TableField(exist = false)
    private List<Integer> deptIds;

    /**
     * 校区ID
     */
    @TableField(exist = false)
    private List<Integer> campusIds;

    /**
     * 校区名称
     */
    @TableField(exist = false)
    private List<String> campusNames;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private List<String> deptNames;

    /**
     * 排序字段
     */
    private transient String sortField;

    /**
     * 排序规则 ascend 升序 descend 降序
     */
    private transient String sortOrder;

    private transient String createTimeFrom;
    private transient String createTimeTo;

    @ApiModelProperty("姓名、手机号、账号")
    @TableField(exist = false)
    private String keyword;

    /**
     * shiro-redis v3.1.0 必须要有 getAuthCacheKey()或者 getId()方法
     * # Principal id field name. The field which you can get unique id to identify this principal.
     * # For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc.
     * # Remember to add getter to this id field. For example, getUserId(), getUserName(), getEmail(), etc.
     * # Default value is authCacheKey or id, that means your principal object has a method called "getAuthCacheKey()" or "getId()"
     *
     * @return userId as Principal id field name
     */
    public Integer getAuthCacheKey() {
        return id;
    }
}
