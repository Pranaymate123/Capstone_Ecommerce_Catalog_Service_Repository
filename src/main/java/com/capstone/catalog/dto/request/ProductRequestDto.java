package com.capstone.catalog.dto.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

	@Size(max = 180, message = "Title must be at most 180 characters")
	private String title;

	@Size(max = 10000, message = "Description must be at most 10000 characters")
	private String description;

	@NotNull(message = "Price is required")
	@PositiveOrZero(message = "Price must be greater than or equal to 0")
	private Double price;

	@PositiveOrZero(message = "Discounted price must be greater than or equal to 0")
	private Double discountedPrice;



	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity must be greater than or equal to 0")
	private Integer quantity;

	@NotNull(message = "Live flag is required")
	private Boolean live;

	@NotNull(message = "Stock flag is required")
	private Boolean stock;

	@Size(max = 500, message = "Image URL must be at most 500 characters")
	private String imageUrl;


	@AssertTrue(message = "Discounted price must be less than or equal to price")
	public boolean isDiscountValid() {
		if (discountedPrice == null || price == null)
			return true;
		return discountedPrice <= price;
	}

}
