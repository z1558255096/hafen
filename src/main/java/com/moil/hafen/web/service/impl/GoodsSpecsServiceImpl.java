package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.GoodsSpecsDao;
import com.moil.hafen.web.domain.GoodsSpecs;
import com.moil.hafen.web.service.GoodsSpecsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsSpecsServiceImpl extends ServiceImpl<GoodsSpecsDao,GoodsSpecs> implements GoodsSpecsService {
}
