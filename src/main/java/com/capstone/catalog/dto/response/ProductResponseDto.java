package com.capstone.catalog.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String productId;
    private String title;
    private String description;
    private Double price;
    private Double discountedPrice;
    private Integer quantity;
    private Boolean live;
    private Boolean stock;
    private String imageUrl;
    private Long categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
