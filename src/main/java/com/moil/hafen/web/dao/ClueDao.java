package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.web.domain.Clue;
import com.moil.hafen.web.vo.ClueVo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 陈子杰
 * @Description (Clue)表持久层接口
 * @Version 1.0.0
 * @Date 2023-12-14 02:35:11
 */
public interface ClueDao extends BaseMapper<Clue> {
    IPage<ClueVo> page(@Param("page") IPage<Clue> page,@Param("clue") Clue clue);
}

