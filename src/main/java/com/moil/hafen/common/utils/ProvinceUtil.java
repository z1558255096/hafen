package com.moil.hafen.common.utils;

import java.util.ArrayList;
import java.util.List;

public class ProvinceUtil {
    public static List<String> provinceList = new ArrayList();
    static {
        provinceList.add("河北");
        provinceList.add("山西");
        provinceList.add("吉林");
        provinceList.add("辽宁");
        provinceList.add("黑龙江");
        provinceList.add("陕西");
        provinceList.add("甘肃");
        provinceList.add("青海");
        provinceList.add("山东");
        provinceList.add("福建");
        provinceList.add("浙江");
        provinceList.add("台湾");
        provinceList.add("河南");
        provinceList.add("湖北");
        provinceList.add("湖南");
        provinceList.add("江西");
        provinceList.add("江苏");
        provinceList.add("安徽");
        provinceList.add("广东");
        provinceList.add("海南");
        provinceList.add("四川");
        provinceList.add("贵州");
        provinceList.add("云南");
        provinceList.add("北京");
        provinceList.add("上海");
        provinceList.add("天津");
        provinceList.add("重庆");
        provinceList.add("内蒙古");
        provinceList.add("新疆");
        provinceList.add("宁夏");
        provinceList.add("广西");
        provinceList.add("西藏");
    }

    public static String getProvince(String address){
        String province = null;
        for (String provinceStr : provinceList) {
            if(address.startsWith(provinceStr)){
                province = provinceStr;
                break;
            }
        }
        return province;
    }

}
