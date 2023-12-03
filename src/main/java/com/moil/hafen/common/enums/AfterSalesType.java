package com.moil.hafen.common.enums;

public enum AfterSalesType {

    仅退款(1),
    退货退款(2),
    换货(3),
    ;
    public int type;
    AfterSalesType(int state){
        this.type=type;
    }
}
