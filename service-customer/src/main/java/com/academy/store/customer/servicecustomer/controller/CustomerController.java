package com.academy.store.customer.servicecustomer.controller;


import com.academy.store.customer.servicecustomer.entity.Customer;
import com.academy.store.customer.servicecustomer.entity.Region;
import com.academy.store.customer.servicecustomer.errors.ErrorMessage;
import com.academy.store.customer.servicecustomer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j // esta anotacion nos permite hacer la anotacion de los logs
@RestController // para indicar que vamos a implementar un servicio REST
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // listar todos los customers
    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomer(){
        List<Customer> allcustomer = customerService.findCustomerAll();
        return ResponseEntity.ok(allcustomer);

    }

    // listar todos los customers
    @GetMapping(value = "/region/{idregion}")
    public ResponseEntity<List<Customer>> listAllCustomerByRegion(@PathVariable Long idregion){
        List<Customer> allcustomerbyregion = customerService.customersByRegion(idregion);
        return ResponseEntity.ok(allcustomerbyregion);

    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("error al introducir dato."); // se registra el log gracias a la anotacion @Slf4j
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        Customer customercreated = customerService.createCustomer(customer);
        log.info("customer created con id: " + customercreated.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(customercreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        customer.setId(id);
        Customer customerupdated = customerService.updateCustomer(customer);
        if(null == customerupdated){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerupdated);
    }

    @DeleteMapping(value = "/eliminar/{id}")
    public ResponseEntity<Customer> deleteOneCustomer(@PathVariable("id") Long id){

        Customer customerdeleted = customerService.deleteCustomer(id);
        if (customerdeleted == null){
            return ResponseEntity.notFound().build();
        }
        log.info("customer deleted");
        return ResponseEntity.ok(customerdeleted);

    }

    private String formatMessage(BindingResult result){

        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .message(errors).build();

        // el objeto errorMessage lo pasamos en un Json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return jsonString;
    }

}
