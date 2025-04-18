package com.example.SpringWEbORDINI.services;

import com.example.SpringWEbORDINI.models.Product;
import com.example.SpringWEbORDINI.models.User;
import com.example.SpringWEbORDINI.repositories.ProductRepository;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired @Qualifier("createFakeProduct")
    ObjectProvider<Product> productObjectProvider;
    public Product saveProdotto(Product product) {
        productRepository.save(product);
        return product;
    }
    // User Method
    public Product createFakeProduct() {
        return productObjectProvider.getObject();
    }
    public Product modifyProdotto(Product product) {
        productRepository.save(product);
        return product;
    }
    public long prodNum(){
        return productRepository.count();
    }
    public void deleteProdotto(Product product) {
        productRepository.delete(product);
    }
    public void deleteProdottoById(Long id) {
        productRepository.deleteById(id);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }
    public boolean existsProductById(Long id) {
        return productRepository.existsById(id);
    }
    public Product getRandomProduct() {
        List<Product> products = getAllProducts();
        if (products.isEmpty()) {
            return null; // Список пуст, вернуть null
        }
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        return products.get(randomIndex);
    }
    public List<Product> getRandomProducts(int count) {
        List<Product> products = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Product product = getRandomProduct();
            products.add(product);
        }
        return products;

    }

}
