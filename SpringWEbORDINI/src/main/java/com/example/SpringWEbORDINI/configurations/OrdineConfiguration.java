package com.example.SpringWEbORDINI.configurations;

import com.example.SpringWEbORDINI.models.Ordine;
import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.services.ProductService;
import com.example.SpringWEbORDINI.services.UserService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

@Configuration
public class OrdineConfiguration {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Bean
    @Scope("prototype")
    public Ordine createCustomOrder() {
        return new Ordine();
    }

}
