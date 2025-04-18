package com.example.SpringWEbORDINI.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="ordini")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"ordini", "products"})
public class Ordine {
//    Un ordine appartiene a un utente e un prodotto.
//            id (Long, generato automaticamente)
//    user (User, relazione molti-a-uno)
//    product (Product, relazione uno-a-molti)
//    totalPrice (BigDecimal, calcolato automaticamente)
//    status (Enum: PENDING, SHIPPED, DELIVERED, CANCELLED)
//    createdAt (LocalDateTime, generato automaticamente)
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="ordine_id")
    private Long id;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING) // Храним в виде строки в БД
    private OrderStatus status;
    @Column(columnDefinition = "DATE")
    private LocalDate createdAt = LocalDate.now();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;




    @ManyToMany
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}
