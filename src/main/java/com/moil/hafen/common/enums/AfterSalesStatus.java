package com.moil.hafen.common.enums;

public enum AfterSalesStatus {

    待审核(100),
    拒绝(101),
    待寄出(102),
    退货待收货(103),
    换货待收货(104),
    已退款(200),
    已换货(201),
    ;
    public int state;
    AfterSalesStatus(int state){
        this.state=state;
    }
}
