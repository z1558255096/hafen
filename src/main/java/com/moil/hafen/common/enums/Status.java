package com.moil.hafen.common.enums;

public enum Status {
    上架(0),
    下架(1),;
    public int state;
    Status(int state){
        this.state=state;
    }
}
