package com.mihaighise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT_QUERY")
public class Product {

    @Id
    private Long id;

    private String name;
    private String description;
    private double price;
}
