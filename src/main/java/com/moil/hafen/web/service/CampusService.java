package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Campus;

import java.util.List;

public interface CampusService extends IService<Campus> {
    IPage<Campus> getPage(QueryRequest request, Campus campus);

    List<Campus> getCampusList(Campus campus);
}
