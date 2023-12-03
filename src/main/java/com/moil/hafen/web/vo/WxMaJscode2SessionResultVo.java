package com.moil.hafen.web.vo;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import lombok.Data;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-10-29 16:01
 **/
@Data
public class WxMaJscode2SessionResultVo extends WxMaJscode2SessionResult {
    private String mobile;
}
