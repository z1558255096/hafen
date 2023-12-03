package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.InviteConfig;

public interface InviteConfigService extends IService<InviteConfig> {
    IPage<InviteConfig> getPage(QueryRequest request, InviteConfig inviteConfig);
}
