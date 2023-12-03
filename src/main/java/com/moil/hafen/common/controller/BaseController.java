package com.moil.hafen.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getRecords());
        rspData.put("total", pageInfo.getTotal());
        rspData.put("code",200);
        return rspData;
    }

    protected Map<String, Object> getDataTable(List<?> list) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", list);
        rspData.put("total", list.size());
        rspData.put("code",200);
        return rspData;
    }


}
