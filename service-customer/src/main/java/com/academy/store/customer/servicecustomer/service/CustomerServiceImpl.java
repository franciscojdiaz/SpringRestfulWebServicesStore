package com.academy.store.customer.servicecustomer.service;

import com.academy.store.customer.servicecustomer.entity.Customer;
import com.academy.store.customer.servicecustomer.entity.Region;
import com.academy.store.customer.servicecustomer.repository.CustomerReposiory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerReposiory customerReposiory;

    @Override
    public List<Customer> findCustomerAll() {
        List<Customer> customerList = customerReposiory.findAll();
        return customerList;
    }




    @Override
    public Customer createCustomer(Customer customer) {
        Customer customercreated = customerReposiory.findByNumberID(customer.getNumberID());
        if(customercreated != null){
            return customercreated;
        }
        customer.setState("CREATED");
        return customerReposiory.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerupdated = getCustomer(customer.getId());
        if (customerupdated == null){
            return null;
        }
        System.out.println("el objeto  customerupdated ...." +customer.getFirstname() +"  ..lastname .. "+ customer.getLastname() );
        customerupdated.setState("UPDATED");
        customerupdated.setFirstname(customer.getFirstname());
        customerupdated.setLastname(customer.getLastname());
        customerupdated.setEmail(customer.getEmail());
        customerupdated.setPhotourl(customer.getPhotourl());
        return customerReposiory.save(customerupdated);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        Customer customerdeleted = getCustomer(id);
        if (customerdeleted == null){
            return null;
        }
        customerdeleted.setState("DELETED");
        return customerReposiory.save(customerdeleted);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerReposiory.findById(id).orElse(null);
    }


    @Override
    public Customer findCustomerByID(String numberid) {
        Customer customerbynumberid = customerReposiory.findByNumberID(numberid);
        return customerbynumberid;
    }

    @Override
    public List<Customer> findCustomerLastName(String lastname) {
        List<Customer> customersbylastname = customerReposiory.findByLastname(lastname);
        return customersbylastname;
    }

    @Override
    public List<Customer> customersByRegion(Long idregion) {
        List<Customer> customersByRegion = customerReposiory.findCustomerByRegion(idregion);
        return customersByRegion;
    }

}
