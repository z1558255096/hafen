package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.domain.Student;

import java.util.List;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-13 09:57
 **/
public interface CustomerService extends IService<Customer> {
    void saveCustomer(Customer customer);

    IPage<Customer> getPage(QueryRequest request, Customer customer);

    List<Customer> getCustomerList(Customer customer);

    List<Customer> getInviteList();

    List<Student> getMyStudent();

    Customer myCenter();
}
