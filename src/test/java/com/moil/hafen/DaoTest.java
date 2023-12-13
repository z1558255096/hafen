package com.moil.hafen;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.dao.ClueDao;
import com.moil.hafen.web.domain.Clue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author 陈子杰
 * @Description TODO
 * @Version 1.0.0
 * @Date 2023/12/14 3:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DaoTest {
    @Resource
    private ClueDao clueDao;

    @Test
    public void test() {
        Page<Clue> page = new Page<>(1,10);
        // IPage<ClueVo> voPage = clueDao.page(page, );
        // log.info("page：{}", voPage);
    }
}
