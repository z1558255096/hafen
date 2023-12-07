package com.moil.hafen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 陈子杰
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/12/6 23:38
 */
@Getter
@AllArgsConstructor
public enum DataScope {
    /**
     * 全部
     */
    ALL(1, "全部"),
    /**
     * 本部门及以下
     */
    DEPT(2, "本部门"),
    /**
     * 本人
     */
    SELF(3, "本人");

    private final Integer scope;

    private final String value;
}
