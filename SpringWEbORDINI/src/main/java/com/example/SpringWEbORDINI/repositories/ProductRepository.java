package com.example.SpringWEbORDINI.repositories;

import com.example.SpringWEbORDINI.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
