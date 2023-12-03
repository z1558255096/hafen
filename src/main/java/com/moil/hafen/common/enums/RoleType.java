package com.moil.hafen.common.enums;

public enum RoleType {
    顾客(0),
    服务师(1),
    后台用户(99);
    public int type;
    RoleType(int type){
        this.type=type;
    }
}
