package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Campus;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 校区service
 * @Version 1.0.0
 * @Date 2023/12/08 12:42
 */
public interface CampusService extends IService<Campus> {
    /**
     * 获取校区列表（分页） -管理后台/小程序
     *
     * @param request 分页请求
     * @param campus  校区
     * @return {@link IPage}<{@link Campus}>
     */
    IPage<Campus> getPage(QueryRequest request, Campus campus);

    List<Campus> getCampusList(Campus campus);

    /**
     * 获取校区列表
     *
     * @return {@link List}<{@link Campus}>
     */
    List<Campus> getList();
}
