package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.domain.Feedback;
import com.moil.hafen.web.service.CustomerService;
import com.moil.hafen.web.service.FeedbackService;
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
@RequestMapping("feedback")
@Api(tags = "意见反馈管理 - 管理后台/小程序")
public class FeedbackController extends BaseController {

    private String message;

    @Resource
    private FeedbackService feedbackService;
    @Resource
    private CustomerService customerService;

    @GetMapping
    @ApiOperation("获取意见反馈列表（分页）")
    public Map<String, Object> page(QueryRequest request, Feedback feedback) {
        IPage<Feedback> page = this.feedbackService.getPage(request, feedback);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加意见反馈信息")
    public Result add(Feedback feedback) throws FebsException {
        try {
            feedback.setCreateTime(new Date());
            feedback.setModifyTime(new Date());
            feedback.setStatus(0);
            int customerId = JWTUtil.getCurrentCustomerId();
            feedback.setCustomerId(customerId);
            Customer customer = customerService.getById(customerId);
            feedback.setNickname(customer.getNickName());
            feedback.setMobile(customer.getMobile());
            return Result.OK(this.feedbackService.save(feedback));
        } catch (Exception e) {
            message = "添加意见反馈信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除意见反馈信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.feedbackService.removeById(id));
        } catch (Exception e) {
            message = "删除意见反馈信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改意见反馈信息")
    public Result update(Feedback feedback) throws FebsException {
        try {
            feedback.setModifyTime(new Date());
            return Result.OK(this.feedbackService.updateById(feedback));
        } catch (Exception e) {
            message = "修改意见反馈信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping("/{id}/reply")
    @ApiOperation("修改意见反馈处理状态")
    public Result reply(@PathVariable Integer id) throws FebsException {
        try {
            Feedback feedback = this.feedbackService.getById(id);
            feedback.setModifyTime(new Date());
            feedback.setStatus(1);
            feedback.setReply(JWTUtil.getCurrentUserName());
            return Result.OK(this.feedbackService.updateById(feedback));
        } catch (Exception e) {
            message = "修改意见反馈信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取意见反馈详情")
    public Result<Feedback> detail(@PathVariable Integer id) {
        return Result.OK(this.feedbackService.getById(id));
    }
}
