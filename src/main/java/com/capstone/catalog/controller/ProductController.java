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

import com.capstone.catalog.dto.request.ProductRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.ProductResponseDto;
import com.capstone.catalog.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/categories/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDto>>  createProduct(@Valid @RequestBody ProductRequestDto productRequestDto ,@PathVariable Long id)
	{
		return ResponseEntity.status(201).body(productService.createProductWithCategory(productRequestDto,id));
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDto>>  getProductById(@PathVariable Long id)
	{
		return ResponseEntity.status(200).body(productService.getProductById(id));
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts(
			 @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
	            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
	            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
	            )
	{
		return ResponseEntity.status(200).body(productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir));
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>>  deleteProductById(@PathVariable Long id)
	{
		return ResponseEntity.status(200).body(productService.deleteProduct(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductResponseDto>>  updateProduct(@RequestBody ProductRequestDto productRequestDto ,@PathVariable Long id)
	{
		return ResponseEntity.status(201).body(productService.updateProductById(id,productRequestDto));
	
	}
	
	@GetMapping("/live-products")
	public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllLiveProducts(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            
			)
	{
		
		
		return ResponseEntity.status(200).body(productService.getAllLive(pageNumber, pageSize, sortBy, sortDir));
	}
	
	@GetMapping("/all/{searchtitle}")
	public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllLiveProducts(
			@PathVariable String searchtitle,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            
			)
	{
		
		return ResponseEntity.status(200).body(productService.searchByTitle(searchtitle,pageNumber, pageSize, sortBy, sortDir));
	}
	
	@PutMapping("/category/{prodId}/{categoryId}")
	public ResponseEntity< ApiResponse<ProductResponseDto>> updateProductsCategory(@PathVariable Long prodId ,@PathVariable Long categoryId)
	{
		return ResponseEntity.status(200).body(productService.updateCategory(prodId, categoryId));
	}
	
	
	@GetMapping("/all/category/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllByCategory(
			@PathVariable Long categoryId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )
	{
		
		return ResponseEntity.status(200).body(productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir));
	}
}
