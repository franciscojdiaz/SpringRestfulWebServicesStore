package com.academy.store.product.controller;


import com.academy.store.product.entity.Category;
import com.academy.store.product.entity.Product;
import com.academy.store.product.errors.ErrorMessage;
import com.academy.store.product.service.ProductService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController // para indicar que vamos a implementar un servicio REST
@RequestMapping(value = "/products") // le pasamos el URI de nuestro recurso. en plural
public class ProductController {

    @Autowired
    private ProductService productService;


    // listar todos los productos por categoria
    @GetMapping
    public ResponseEntity<List<Product>> listProductByCategory(@RequestParam(name = "categoryId", required = false) Long categoryId){

        List<Product> products = new ArrayList<>();
        if (null == categoryId){
            products = productService.listAllproduct();
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    //obtener un producto por id
    @GetMapping(value = "/{id}")
    private ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);

        if (null == product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    //crear un producto
    @PostMapping
    private ResponseEntity<Product> crearProduct(@Valid  @RequestBody Product product, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("error al crear el producto : " + product.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(bindingResult));
        }
        Product productcreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productcreated);
    }

    //actualizar un producto

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productupdated = productService.updateProduct(product);

        if (productupdated == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productupdated);
    }

    // eliminar un producto.
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteOneProduct(@PathVariable("id") Long id){

        Product productdeleted = productService.deleteProduct(id);
            if (productdeleted == null){
                return ResponseEntity.notFound().build();
            }else {
                log.info("producto eliminado con id : " + id);
            }
            return ResponseEntity.ok(productdeleted);
    }

    // actualizar el stock del producto.

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProdutc(@PathVariable Long id, @RequestParam(name = "quantity", required = true) Double quantity){

            Product stockupdated = productService.updateStock(id, quantity);
                if (stockupdated == null){
                    return ResponseEntity.notFound().build();
                }
                return ResponseEntity.ok(stockupdated);
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
