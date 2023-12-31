package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.StaffManagement;
import com.moil.hafen.web.service.StaffManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 管理后台-内部管理-员工管理控制员
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping("staffManagement")
@Api(tags = "员工管理")
public class StaffManagementController extends BaseController {

    private String message;

    @Resource
    private StaffManagementService staffManagementService;

    /**
     * 获取员工列表（分页）
     *
     * @param request         要求
     * @param staffManagement 员工管理
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取员工列表（分页）")
    public Map<String, Object> page(QueryRequest request, StaffManagement staffManagement) {
        IPage<StaffManagement> page = this.staffManagementService.getPage(request, staffManagement);
        return getDataTable(page);
    }

    /**
     * 添加员工信息
     *
     * @param staffManagement 员工管理
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加员工信息")
    public Result add(StaffManagement staffManagement) throws FebsException {
        try {
            staffManagement.setCreateTime(new Date());
            staffManagement.setModifyTime(new Date());
            return Result.OK(this.staffManagementService.save(staffManagement));
        } catch (Exception e) {
            message = "添加员工信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改员工信息
     *
     * @param staffManagement 员工管理
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(StaffManagement staffManagement) throws FebsException {
        try {
            staffManagement.setModifyTime(new Date());
            return Result.OK(this.staffManagementService.updateById(staffManagement));
        } catch (Exception e) {
            message = "修改员工信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改员工人事状态
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("修改员工人事状态")
    public Result changeStatus(@PathVariable Integer id,int status) throws FebsException {
        try {
            StaffManagement staffManagement = this.staffManagementService.getById(id);
            staffManagement.setStatus(status);
            staffManagement.setModifyTime(new Date());
            return Result.OK(this.staffManagementService.updateById(staffManagement));
        } catch (Exception e) {
            message = "修改员工信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取员工详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link StaffManagement}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取员工详情")
    public Result<StaffManagement> detail(@PathVariable Integer id) {
        return Result.OK(this.staffManagementService.getById(id));
    }
}
