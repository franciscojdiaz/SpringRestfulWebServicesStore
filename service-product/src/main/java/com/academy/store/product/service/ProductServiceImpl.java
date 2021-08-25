package com.academy.store.product.service;

import com.academy.store.product.entity.Category;
import com.academy.store.product.entity.Product;
import com.academy.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{


    // se hace la inyeccion de dependecia pero con @RequiredArgsConstructor
    private final ProductRepository productRepository;


    @Override
    public List<Product> listAllproduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product deleteProduct(Long id) {
        // validamos si el producto existe
        Product productDB = getProduct(id);

        if (null == productDB){
            return  null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAT(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        // validamos si el producto existe
        Product productDB = getProduct(product.getId());

        if (null == productDB){
            return  null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        // validamos si el producto existe
        Product productDB = getProduct(id);

        if (null == productDB){
            return  null;
        }

        Double stock = productDB.getStock() + quantity;
        productDB.setStock(stock);
        return productRepository.save(productDB);
    }
}
