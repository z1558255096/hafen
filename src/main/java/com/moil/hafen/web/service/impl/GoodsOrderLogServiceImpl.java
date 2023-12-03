package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.GoodsOrderLogDao;
import com.moil.hafen.web.domain.GoodsOrderLog;
import com.moil.hafen.web.service.GoodsOrderLogService;
import org.springframework.stereotype.Service;

@Service
public class GoodsOrderLogServiceImpl extends ServiceImpl<GoodsOrderLogDao,GoodsOrderLog> implements GoodsOrderLogService {
}
