package com.academy.store.customer.servicecustomer.service;

import com.academy.store.customer.servicecustomer.entity.Customer;
import com.academy.store.customer.servicecustomer.entity.Region;

import java.util.List;

public interface CustomerService {


    public List<Customer> findCustomerAll();


    public Customer createCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public Customer deleteCustomer(Long id);
    public Customer getCustomer(Long id);
    public Customer findCustomerByID(String numberid);
    public List<Customer> findCustomerLastName(String lastname);
    public List<Customer> customersByRegion(Long idregion);

}
