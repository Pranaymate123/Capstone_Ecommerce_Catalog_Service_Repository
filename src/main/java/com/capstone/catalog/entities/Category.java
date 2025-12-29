package com.capstone.catalog.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false , unique = true , updatable = false)
    private String categoryId;

    @Column( length = 100, nullable = false)
    private String title;

    @Column( length = 500)
    private String description;

    @Column(length = 1000)
    private String categoryImageUrl;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    
    // Helper methods to keep both sides in sync
       public void addProduct(Product product) {
           products.add(product);
           product.setCategory(this);
       }

       public void removeProduct(Product product) {
           products.remove(product);
           product.setCategory(null);
       }

}