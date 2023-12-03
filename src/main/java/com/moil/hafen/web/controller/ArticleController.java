package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Article;
import com.moil.hafen.web.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"article"})
@Api(tags = "文章管理")
public class ArticleController extends BaseController {

    private String message;

    @Resource
    private ArticleService articleService;

    @GetMapping
    @ApiOperation("获取文章列表（分页）")
    public Map<String, Object> page(QueryRequest request, Article article) {
        IPage<Article> page = this.articleService.getPage(request, article);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加文章信息")
    public Result add(Article article) throws FebsException {
        try {
            article.setCreateTime(new Date());
            article.setModifyTime(new Date());
            article.setDelFlag(0);
            article.setLikeCount(0);
            article.setCollectCount(0);
            return Result.OK(this.articleService.save(article));
        } catch (Exception e) {
            message = "添加文章信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Article article = articleService.getById(id);
            article.setDelFlag(1);
            article.setModifyTime(new Date());
            return Result.OK(this.articleService.updateById(article));
        } catch (Exception e) {
            message = "删除文章信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改文章信息")
    public Result update(Article article) throws FebsException {
        try {
            article.setModifyTime(new Date());
            return Result.OK(this.articleService.updateById(article));
        } catch (Exception e) {
            message = "修改文章信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架文章")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            Article article = articleService.getById(id);
            article.setStatus(status);
            article.setModifyTime(new Date());
            return Result.OK(this.articleService.updateById(article));
        } catch (Exception e) {
            message = "上下架文章失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取文章详情")
    public Result<Article> detail(@PathVariable Integer id) {
        return Result.OK(this.articleService.getById(id));
    }

}
