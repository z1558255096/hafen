package com.moil.hafen.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.system.dao.RegionDao;
import com.moil.hafen.system.domain.Region;
import com.moil.hafen.system.service.RegionService;
import org.springframework.stereotype.Service;

/**
 * @program: sojourn
 * @description:
 * @author: Moil
 * @create: 2022-06-16 19:58
 **/
@Service
public class RegionServiceImpl extends ServiceImpl<RegionDao, Region> implements RegionService {
}
