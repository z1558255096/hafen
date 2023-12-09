package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.HafenCoinObtain;

/**
 * @Author 陈子杰
 * @Description 哈奋币规则-获取
 * @Version 1.0.0
 * @Date 2023/12/10 05:08
 */
public interface HafenCoinObtainService extends IService<HafenCoinObtain> {
    /**
     * 获取哈奋币规则列表（分页）
     *
     * @param request         要求
     * @param hafenCoinObtain 哈芬币获得
     * @return {@link IPage}<{@link HafenCoinObtain}>
     */
    IPage<HafenCoinObtain> getPage(QueryRequest request, HafenCoinObtain hafenCoinObtain);
}
