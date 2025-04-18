package com.example.SpringWEbORDINI.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {
//    L'entità deve contenere i seguenti campi:
//    id (Long, generato automaticamente)
//    name (String, non nullo)
//    description (String)
//    price (BigDecimal, non nullo)
//    createdAt (LocalDateTime, generato automaticamente)
    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(columnDefinition = "DATE")
    private LocalDate createdAt = LocalDate.now();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine_id", nullable = true)  // Связь с Ordine через внешний ключ
    private Ordine ordine;

}
