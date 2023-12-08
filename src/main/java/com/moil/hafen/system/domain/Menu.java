package com.moil.hafen.system.domain;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moil.hafen.common.domain.BaseTree;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 菜单表实体类
 * @Version 1.0.0
 * @Date 2023/12/07 04:49
 */
@Data
@TableName("t_menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements BaseTree<Integer>, Serializable {
    private static final long serialVersionUID = 7187628714679791771L;

    public static final String TYPE_MENU = "0";

    public static final String TYPE_BUTTON = "1";

    @TableId(value = "MENU_ID", type = IdType.AUTO)
    private Integer menuId;

    @ApiModelProperty("父菜单ID")
    private Integer parentId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    private Date createTime;

    private Date modifyTime;

    @ApiModelProperty("子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 删除标识：0-正常;1-删除
     */
    private Integer delFlag;

    @Override
    public Integer id() {
        return this.menuId;
    }

    @Override
    public Integer parentId() {
        return this.parentId;
    }

    @Override
    public boolean rootNode() {
        return ObjUtil.equals(this.parentId, 0);
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}