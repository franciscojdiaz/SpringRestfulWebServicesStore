package com.academy.store.product.service;

import com.academy.store.product.entity.Category;
import com.academy.store.product.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> listAllproduct();
    public List<Product> findByCategory(Category category);
    public Product getProduct(Long id);
    public Product deleteProduct(Long id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public Product updateStock(Long id, Double quantity);


}
