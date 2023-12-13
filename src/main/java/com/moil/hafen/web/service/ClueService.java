package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Clue;
import com.moil.hafen.web.vo.ClueVo;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description (Clue)表业务层接口
 * @Version 1.0.0
 * @Date 2023-12-14 02:35:11
 */
public interface ClueService {
    /**
     * 新增线索
     *
     * @param clue 线索
     */
    void add(Clue clue);

    /**
     * 分页查询线索列表
     *
     * @param queryRequest 查询请求
     * @param clue         线索
     * @return {@link List}<{@link ClueVo}>
     */
    IPage<ClueVo> page(QueryRequest queryRequest, Clue clue);
}
