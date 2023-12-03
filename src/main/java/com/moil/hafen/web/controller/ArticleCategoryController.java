package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.ArticleCategory;
import com.moil.hafen.web.service.ArticleCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"articleCategory"})
@Api(tags = "文章分类管理")
public class ArticleCategoryController extends BaseController {

    private String message;

    @Resource
    private ArticleCategoryService articleCategoryService;

    @GetMapping
    @ApiOperation("获取文章分类列表（分页）")
    public Map<String, Object> page(QueryRequest request, ArticleCategory articleCategory) {
        IPage<ArticleCategory> page = this.articleCategoryService.getPage(request, articleCategory);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加文章分类信息")
    public Result add(ArticleCategory articleCategory) throws FebsException {
        try {
            articleCategory.setCreateTime(new Date());
            articleCategory.setModifyTime(new Date());
            articleCategory.setDelFlag(0);
            return Result.OK(this.articleCategoryService.save(articleCategory));
        } catch (Exception e) {
            message = "添加文章分类信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章分类信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setDelFlag(1);
            articleCategory.setId(id);
            articleCategory.setModifyTime(new Date());
            return Result.OK(this.articleCategoryService.updateById(articleCategory));
        } catch (Exception e) {
            message = "删除文章分类信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改文章分类信息")
    public Result update(ArticleCategory articleCategory) throws FebsException {
        try {
            articleCategory.setModifyTime(new Date());
            return Result.OK(this.articleCategoryService.updateById(articleCategory));
        } catch (Exception e) {
            message = "修改文章分类信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架文章分类")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            ArticleCategory articleCategory = articleCategoryService.getById(id);
            articleCategory.setStatus(status);
            articleCategory.setModifyTime(new Date());
            return Result.OK(this.articleCategoryService.updateById(articleCategory));
        } catch (Exception e) {
            message = "上下架文章分类失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取文章分类详情")
    public Result<ArticleCategory> detail(@PathVariable Integer id) {
        return Result.OK(this.articleCategoryService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取文章分类列表")
    public Result list() {
        List<ArticleCategory> list = this.articleCategoryService.list();
        return Result.OK(list);
    }

}
