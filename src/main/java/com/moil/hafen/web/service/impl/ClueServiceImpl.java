package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ClueDao;
import com.moil.hafen.web.domain.Clue;
import com.moil.hafen.web.service.ClueService;
import com.moil.hafen.web.vo.ClueVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author 陈子杰
 * @Description (Clue)表业务层实现类
 * @Version 1.0.0
 * @Date 2023-12-14 02:35:11
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private ClueDao clueDao;

    @Override
    public void add(Clue clue) {
        clueDao.insert(clue);
    }

    @Override
    public IPage<ClueVo> page(QueryRequest queryRequest, Clue clue) {
        Page<Clue> page = new Page<>();
        SortUtil.handlePageSort(queryRequest, page, true);
        IPage<ClueVo> voPage = clueDao.page(page, clue);
        return voPage;
    }
}
