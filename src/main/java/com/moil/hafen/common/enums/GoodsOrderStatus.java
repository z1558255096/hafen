package com.moil.hafen.common.enums;

public enum GoodsOrderStatus {
    待支付(400),
    关闭(401),
    待发货(600),
    已发货待收货(700),
    已完成(1000),
    ;
    public int state;
    GoodsOrderStatus(int state){
        this.state=state;
    }
}
