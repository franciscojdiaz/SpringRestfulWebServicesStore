package com.academy.store.product;


import com.academy.store.product.entity.Category;
import com.academy.store.product.entity.Product;
import com.academy.store.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    // creamos un metodo donde pragramamos nuestra prueba unitaria.

    @Test
    public void whenFindByCategory_thenReturnListProducts(){

        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1l).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("20"))
                .status("Created")
                .createAT(new Date()).build();

                productRepository.save(product01);

        List<Product> founds = productRepository.findByCategory(product01.getCategory());

        Assertions.assertThat(founds.size()).isEqualTo(1);

    }
}
