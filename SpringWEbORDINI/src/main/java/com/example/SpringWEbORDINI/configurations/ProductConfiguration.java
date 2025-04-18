package com.example.SpringWEbORDINI.configurations;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

@Configuration
public class ProductConfiguration {

    @Bean
    @Scope("prototype")
    public Product createCustomProduct() {
        return new Product();
    }

    @Bean
    @Scope("prototype")
    public Product createFakeProduct() {
        Faker fake = new Faker(new Locale("it-IT"));
        Product product = new Product();

        product.setName(fake.commerce().productName());
        product.setDescription(fake.lorem().sentence(10));
        product.setPrice(fake.number().randomDouble(2,2,10));
        return product;
    }

}
