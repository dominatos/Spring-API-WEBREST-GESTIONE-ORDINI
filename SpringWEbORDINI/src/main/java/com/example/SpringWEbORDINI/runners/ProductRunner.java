package com.example.SpringWEbORDINI.runners;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ProductRunner implements CommandLineRunner {
    @Autowired
    ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("ProductRunner...");
        //generareNumProdotti(100);
        //Product product=productService.getRandomProduct();



        //System.out.println(product);
    }
    public void generareNumProdotti(int n) {
        for (int i = 0; i < n; i++) {
            productService.saveProdotto(productService.createFakeProduct());
        }
        System.out.println("Database inizializzato con " + n + " prodotti!!!") ;
    }

}