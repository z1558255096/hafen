package com.moil.hafen.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * 支付工具类
 * 
 * @author zhangyg
 * @version $Id: PayUtil.java, v 0.1 2016年11月11日 上午11:45:37 zhangyg Exp $
 */
public class PayUtil {

    public static final String PATTERN_DATE_TIMES      = "yyyyMMddHHmmsss";

    /**
     *   请求流水号  20位
     *   
     *              开通协议时必填。
     * 
     * @return
     */
    public static String generateOrderNo(String prefix) {
        if(StringUtils.isBlank(prefix)){
            prefix = "";
        }
        //yyyyMMddHHmmsss + 7位随机数字
        return prefix+getTimeByPattern(PATTERN_DATE_TIMES) + generateRandomInt(5);
    }

    public static String getTimeByPattern(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(System.currentTimeMillis());
    }
    /**
     * 获取指定长度(1位到9位)的随机数字字符串
     *          
     * @param length  大于1  小于9
     * @return
     */
    public static String generateRandomInt(int length) {
        length = length < 1 ? 1 : length;
        length = length > 9 ? 9 : length;
        int max = ((int) Math.pow(10, length)) - 1;
        int min = (int) Math.pow(10, length - 1);
        return String.valueOf((int) (Math.random() * (max - min) + min));
    }

}
