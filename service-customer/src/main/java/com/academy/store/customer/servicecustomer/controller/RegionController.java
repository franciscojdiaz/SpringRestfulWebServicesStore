package com.academy.store.customer.servicecustomer.controller;


import com.academy.store.customer.servicecustomer.entity.Region;
import com.academy.store.customer.servicecustomer.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> allRegions(){
        log.info("mostrando las regiones");
        return ResponseEntity.ok(regionService.getAllRegions());
    }

}
