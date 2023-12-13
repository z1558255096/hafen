package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.UserTag;

/**
 * @Author 陈子杰
 * @Description 用户标签service
 * @Version 1.0.0
 * @Date 2023/12/12 11:05
 */
public interface UserTagService extends IService<UserTag> {
    IPage<UserTag> getPage(QueryRequest request, UserTag userTag);
}
