package com.moil.hafen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 陈子杰
 * @Description 业务范围枚举类
 * @Version 1.0.0
 * @Date 2023/12/7 11:57
 */
@Getter
@AllArgsConstructor
public enum BusinessScope {
    All(0, "科技营、体适能"),
    Science(1, "科技营"),
    Fitness(2, "体适能");

    private final Integer code;
    private final String desc;
}
