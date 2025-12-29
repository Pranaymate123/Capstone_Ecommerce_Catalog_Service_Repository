package com.capstone.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {
	
	 	@NotBlank(message = "Title is required")
	    @Size(max = 100, message = "Title must be at most 100 characters")
	    private String title;

	    @Size(max = 500, message = "Description must be at most 500 characters")
	    private String description;

	    @Size(max = 1000, message = "Category image URL must be at most 1000 characters")
	    private String categoryImageUrl;
	    
}
