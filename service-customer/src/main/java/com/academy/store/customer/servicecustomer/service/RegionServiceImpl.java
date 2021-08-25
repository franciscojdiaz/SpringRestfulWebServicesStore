package com.academy.store.customer.servicecustomer.service;


import com.academy.store.customer.servicecustomer.entity.Region;
import com.academy.store.customer.servicecustomer.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }
}
