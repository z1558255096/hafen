package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.CustomerService;
import com.moil.hafen.web.service.HafenCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"customer"})
@Api(tags = "用户管理管理 - 管理后台")
public class CustomerController extends BaseController {

    private String message;

    @Resource
    private CustomerService customerService;
    @Resource
    private HafenCoinService hafenCoinService;

    @GetMapping
    @ApiOperation("获取用户管理列表（分页）")
    public Map<String, Object> page(QueryRequest request, Customer customer) {
        IPage<Customer> page = this.customerService.getPage(request, customer);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加用户管理信息")
    public Result add(Customer customer) throws FebsException {
        try {
            customer.setCreateTime(new Date());
            customer.setModifyTime(new Date());
            return Result.OK(this.customerService.save(customer));
        } catch (Exception e) {
            message = "添加用户管理信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户管理信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.customerService.removeById(id));
        } catch (Exception e) {
            message = "删除用户管理信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改用户管理信息")
    public Result update(Customer customer) throws FebsException {
        try {
            if(customer.getId() == null){
                customer.setId(JWTUtil.getCurrentCustomerId());
            }
            customer.setModifyTime(new Date());
            return Result.OK(this.customerService.updateById(customer));
        } catch (Exception e) {
            message = "修改用户管理信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/tag")
    @ApiOperation("修改用户标签")
    public Result updateTag(@PathVariable Integer id,String tag) throws FebsException {
        try {
            Customer customer = this.customerService.getById(id);
            customer.setModifyTime(new Date());
            customer.setTag(tag);
            return Result.OK(this.customerService.updateById(customer));
        } catch (Exception e) {
            message = "修改用户标签失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/updateParent")
    @ApiOperation("修改用户上级")
    public Result updateParent(Integer parentId,List<Integer> userIds) throws FebsException {
        try {
            LambdaUpdateWrapper<Customer> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Customer::getParentId,parentId).in(Customer::getId,userIds);
            this.customerService.update(updateWrapper);
            return Result.OK();
        } catch (Exception e) {
            message = "修改用户管理信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取用户管理详情")
    public Result<Customer> detail(@PathVariable Integer id) {
        Customer customer = this.customerService.getById(id);
        if(customer != null){
            int coin = hafenCoinService.getTotalCoin(id);
            customer.setHafenCoin(coin);
        }
        return Result.OK();
    }
    @GetMapping("/export")
    @ApiOperation("导出用户管理列表")
    public void export(Customer customer, HttpServletResponse response) throws FebsException {
        try {
            List<Customer> contestantInfoList = this.customerService.getCustomerList(customer);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("用户管理列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Customer.class).sheet("用户管理列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/getInviteList")
    @ApiOperation("邀请推广列表")
    public Result<List<Customer>> getInviteList() throws FebsException {
        return Result.OK(this.customerService.getInviteList());
    }
    @GetMapping("/getMyStudent")
    @ApiOperation("我的学员列表")
    public Result<List<Student>> getMyStudent() throws FebsException {
        return Result.OK(this.customerService.getMyStudent());
    }

    @GetMapping("/myCenter")
    @ApiOperation("我的个人中心")
    public Result<Customer> myCenter() throws FebsException {
        return Result.OK(this.customerService.myCenter());
    }


}
