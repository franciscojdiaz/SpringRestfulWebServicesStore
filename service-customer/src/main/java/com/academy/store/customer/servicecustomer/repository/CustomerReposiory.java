package com.academy.store.customer.servicecustomer.repository;

import com.academy.store.customer.servicecustomer.entity.Customer;
import com.academy.store.customer.servicecustomer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerReposiory extends JpaRepository<Customer, Long> {

        public Customer findByNumberID(String numberid);
        public List<Customer> findByLastname(String lastname);
        @Query("select c from Customer c where c.region.id = :idregion")
        public List<Customer> findCustomerByRegion(@Param("idregion") Long idregion);


}
