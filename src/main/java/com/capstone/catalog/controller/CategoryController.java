package com.capstone.catalog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.catalog.dto.request.CategoryRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.CategoryResponseDto;
import com.capstone.catalog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<CategoryResponseDto>> createCategory(@Valid @RequestBody CategoryRequestDto requestDto)
	{
		return ResponseEntity.status(201).body(categoryService.create(requestDto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity< ApiResponse<CategoryResponseDto>> getCategoryById(@PathVariable Long id)
	{
		return ResponseEntity.status(200).body(categoryService.getById(id));
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getAllCategories(
			 @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
	            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
	            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
	            )
	{
		return ResponseEntity.status(200).body(categoryService.getAll(pageNumber, pageSize, sortBy, sortDir));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id)
	{
		
		return ResponseEntity.status(200).body(categoryService.deleteCategory(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory( @RequestBody CategoryRequestDto requestDto , @PathVariable Long id)
	{
		return ResponseEntity.status(201).body(categoryService.updateCategory(requestDto,id));
	}
	
	
	
	
}
