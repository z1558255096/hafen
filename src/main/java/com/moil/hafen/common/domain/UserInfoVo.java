package com.moil.hafen.common.domain;

import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author 陈子杰
 * @Description 用户登录返回对象
 * @Version 1.0.0
 * @Date 2023/12/6 21:52
 */
@Data
@ApiModel("用户登录返回对象")
public class UserInfoVo {
    @ApiModelProperty("用户信息")
    private User user;

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时间")
    private String exipreTime;

    // @ApiModelProperty("权限集合")
    // private Set<String> permissions;

    @ApiModelProperty("菜单集合")
    private List<Menu> menus;

    @ApiModelProperty("角色集合")
    private Set<String> roles;
}
