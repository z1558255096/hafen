package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CustomerDao;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.domain.HafenCoin;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.CustomerService;
import com.moil.hafen.web.service.HafenCoinService;
import com.moil.hafen.web.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-13 10:06
 **/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {
    @Resource
    private HafenCoinService hafenCoinService;
    @Resource
    private StudentService studentService;

    @Override
    public void saveCustomer(Customer customer) {

    }

    @Override
    public IPage<Customer> getPage(QueryRequest request, Customer customer) {
        Page<Customer> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(customer));
    }

    @Override
    public List<Customer> getCustomerList(Customer customer) {
        return this.list(getCondition(customer));
    }

    @Override
    public List<Customer> getInviteList() {
        int customerId = JWTUtil.getCurrentCustomerId();
        return this.list(new LambdaQueryWrapper<Customer>().eq(Customer::getParentId,customerId));
    }

    @Override
    public List<Student> getMyStudent() {
        int customerId = JWTUtil.getCurrentCustomerId();
        return studentService.list(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId,customerId));
    }

    @Override
    public Customer myCenter() {
        int customerId = JWTUtil.getCurrentCustomerId();
        Customer customer = this.getById(customerId);
        HafenCoin hafenCoin = hafenCoinService.getOne(new QueryWrapper<HafenCoin>().select("ifnull(sum(coin),0) as totalCoin").eq("customer_id", customerId));
        customer.setHafenCoin(hafenCoin.getTotalCoin());
        return customer;

    }

    private LambdaQueryWrapper<Customer> getCondition(Customer customer){
        LambdaQueryWrapper<Customer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(customer.getId() != null){
            lambdaQueryWrapper.eq(Customer::getId, customer.getId());
        }
        if(StringUtils.isNotBlank(customer.getSource())){
            lambdaQueryWrapper.eq(Customer::getSource, customer.getSource());
        }
        if(StringUtils.isNotBlank(customer.getNickName())){
            lambdaQueryWrapper.eq(Customer::getNickName, customer.getNickName());
        }
        if(StringUtils.isNotBlank(customer.getMobile())){
            lambdaQueryWrapper.likeRight(Customer::getMobile, customer.getMobile());
        }
        if(StringUtils.isNotBlank(customer.getTag())){
            lambdaQueryWrapper.like(Customer::getTag, customer.getTag());
        }
        if(StringUtils.isNotBlank(customer.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Customer::getCreateTime,customer.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(customer.getCreateTimeTo())){
            lambdaQueryWrapper.le(Customer::getCreateTime,customer.getCreateTimeTo()+" 23:59:59");
        }
        return lambdaQueryWrapper;
    }
}
