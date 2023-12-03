package com.moil.hafen.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Notice;
import com.moil.hafen.web.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
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
@RequestMapping({"notice"})
@Api(tags = "公告管理")
public class NoticeController extends BaseController {

    private String message;

    @Resource
    private NoticeService noticeService;

    @GetMapping
    @ApiOperation("获取公告列表（分页）")
    public Map<String, Object> page(QueryRequest request, Notice notice) {
        IPage<Notice> page = this.noticeService.getPage(request, notice);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加公告信息")
    public Result add(Notice notice) throws FebsException {
        try {
            List<String> strings = Jsoup.parse(notice.getContent()).getElementsByAttribute("img").eachAttr("src");
            String img = CollectionUtil.join(strings,";");
            notice.setCreateTime(new Date());
            notice.setModifyTime(new Date());
            notice.setDelFlag(0);
            notice.setImg(img);
            return Result.OK(this.noticeService.save(notice));
        } catch (Exception e) {
            message = "添加公告信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公告信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Notice notice = noticeService.getById(id);
            notice.setDelFlag(1);
            notice.setModifyTime(new Date());
            return Result.OK(this.noticeService.updateById(notice));
        } catch (Exception e) {
            message = "删除公告信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改公告信息")
    public Result update(Notice notice) throws FebsException {
        try {
            notice.setModifyTime(new Date());
            return Result.OK(this.noticeService.updateById(notice));
        } catch (Exception e) {
            message = "修改公告信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeRemind")
    @ApiOperation("修改公告强提醒状态")
    public Result changeRemind(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            Notice notice = noticeService.getById(id);
            notice.setRemind(status);
            notice.setModifyTime(new Date());
            return Result.OK(this.noticeService.updateById(notice));
        } catch (Exception e) {
            message = "修改公告强提醒状态失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeShow")
    @ApiOperation("修改公告首页展示状态")
    public Result changeShow(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            Notice notice = noticeService.getById(id);
            notice.setMainStatus(status);
            notice.setModifyTime(new Date());
            return Result.OK(this.noticeService.updateById(notice));
        } catch (Exception e) {
            message = "修改公告首页展示状态失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公告详情")
    public Result<Notice> detail(@PathVariable Integer id) {
        return Result.OK(this.noticeService.getById(id));
    }

}
