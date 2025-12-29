package com.capstone.catalog.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.catalog.dto.request.ProductRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.CategoryResponseDto;
import com.capstone.catalog.dto.response.PaginationInfo;
import com.capstone.catalog.dto.response.ProductResponseDto;
import com.capstone.catalog.entities.Category;
import com.capstone.catalog.entities.Product;
import com.capstone.catalog.exceptions.InvalidArgumentException;
import com.capstone.catalog.exceptions.RecordNotFoundException;
import com.capstone.catalog.repository.CategoryRepository;
import com.capstone.catalog.repository.ProductRepository;
import com.capstone.catalog.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ApiResponse<ProductResponseDto> createProductWithCategory(ProductRequestDto requestDto, Long id) {

		if(id<=0)
		{
			throw new InvalidArgumentException("CategoryId given is invalid");
		}
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Category Not Found With id " + id));

		Product product = mapper.map(requestDto, Product.class);
		// creating the unique productId at our end

		String productId = UUID.randomUUID().toString();
		
		product.setProductId(productId);
		product.setCategory(category);
		product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

		Product savedProduct = productRepository.save(product);

		return ApiResponse.success(mapper.map(savedProduct, ProductResponseDto.class),
				"Prouct Added to the category Successfully");
	}

	@Override
	public ApiResponse<ProductResponseDto> getProductById(Long id) {
		
		if(id<=0)
		{
			throw new InvalidArgumentException("ProductId given is invalid");
		}
		
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Product Not Found with given Id " + id));

		return ApiResponse.success(mapper.map(product, ProductResponseDto.class), "Product Fetched SuccessFully");
	}

	@Override
	public ApiResponse<List<ProductResponseDto>> getAllProducts(int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> page = productRepository.findAll(pageable);

		PaginationInfo paginationInfo = PaginationInfo.builder().pageNumber(page.getNumber()).pageSize(page.getSize())
				.lastPage(page.isLast()).totalElements(page.getTotalElements()).hasNext(page.hasNext())
				.hasPreviuos(page.hasPrevious()).totalPages(page.getTotalPages()).build();

		List<ProductResponseDto> content = page.getContent().stream().map(c -> mapper.map(c, ProductResponseDto.class))
				.toList();

		return ApiResponse.successWithPagination(content, paginationInfo);
	}

	@Override
	public ApiResponse<Void> deleteProduct(Long id) {
		
		if(id<=0)
		{
			throw new InvalidArgumentException("ProductId given is invalid");
		}
		
		Boolean flag = productRepository.existsById(id);
		
		if(!flag)
		{
			throw new RecordNotFoundException("Product Not Found with given Id " + id);
		}
		
		productRepository.deleteById(id);

		return ApiResponse.success("Product Deleted Successfully...");
	}

	@Override
	public ApiResponse<ProductResponseDto> updateProductById(Long id, ProductRequestDto requestDto) {
		
		if(id<=0)
		{
			throw new InvalidArgumentException("ProductId given is invalid");
		}
		
		
		Product existing =productRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Product Not Found with the given Id " + id));

		if (requestDto.getPrice() != null && requestDto.getPrice() <= 0) {
			throw new IllegalArgumentException("Price must be positive");
		}
		
		if (requestDto.getDiscountedPrice() != null && requestDto.getDiscountedPrice() < 0) {
			throw new IllegalArgumentException("Discounted price cannot be negative");
		}
		
		if (requestDto.getPrice() != null && requestDto.getDiscountedPrice() != null) {
			if (requestDto.getDiscountedPrice() > requestDto.getPrice()) {

				throw new IllegalArgumentException("Discounted price cannot be greater than price");
			}
		}
		
		if (requestDto.getQuantity() != null && requestDto.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be positive");
		}
		
		
		 // 3) Apply updates (partial update: only non-null fields)
	    // DO NOT update: productId, createdAt
	    if (requestDto.getTitle() != null) {
	        existing.setTitle(requestDto.getTitle());
	    }
	    if (requestDto.getDescription() != null) {
	        existing.setDescription(requestDto.getDescription());
	    }
	    if (requestDto.getPrice() != null) {
	        existing.setPrice(requestDto.getPrice());
	    }
	    if (requestDto.getDiscountedPrice() != null) {
	        existing.setDiscountedPrice(requestDto.getDiscountedPrice());
	    }
	    if (requestDto.getQuantity() != null) {
	        existing.setQuantity(requestDto.getQuantity());
	    }
	    if (requestDto.getLive() != null) {
	        existing.setLive(requestDto.getLive());
	    }
	    if (requestDto.getStock() != null) {
	        existing.setStock(requestDto.getStock());
	    }
	    if (requestDto.getImageUrl() != null) {
	        existing.setImageUrl(requestDto.getImageUrl());
	    }



	    existing.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

	    // 5) Persist
	    Product saved = productRepository.save(existing);

		return ApiResponse.success(mapper.map(saved, ProductResponseDto.class),"Product Updated Successfully");
	}

	@Override
	public ApiResponse<List<ProductResponseDto>> getAllLive(int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		
		Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> page = productRepository.findByLiveTrue(pageable);

		PaginationInfo paginationInfo = PaginationInfo.builder().pageNumber(page.getNumber()).pageSize(page.getSize())
				.lastPage(page.isLast()).totalElements(page.getTotalElements()).hasNext(page.hasNext())
				.hasPreviuos(page.hasPrevious()).totalPages(page.getTotalPages()).build();

		List<ProductResponseDto> content = page.getContent().stream().map(c -> mapper.map(c, ProductResponseDto.class))
				.toList();

		return ApiResponse.successWithPagination(content, paginationInfo);
		

	}

	
	
	@Override
	public ApiResponse<List<ProductResponseDto>> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> page = productRepository.findByTitleContainingIgnoreCase(subTitle,pageable);

		PaginationInfo paginationInfo = PaginationInfo.builder().pageNumber(page.getNumber()).pageSize(page.getSize())
				.lastPage(page.isLast()).totalElements(page.getTotalElements()).hasNext(page.hasNext())
				.hasPreviuos(page.hasPrevious()).totalPages(page.getTotalPages()).build();

		List<ProductResponseDto> content = page.getContent().stream().map(c -> mapper.map(c, ProductResponseDto.class))
				.toList();

		return ApiResponse.successWithPagination(content, paginationInfo);
	}

	@Override
	public ApiResponse<ProductResponseDto> updateCategory(Long productId, Long categoryId) {

		if(productId <=0)
		{
			throw new InvalidArgumentException("productId given is invalid");
		}
		
		if(categoryId<=0)
		{
			throw new InvalidArgumentException("categoryId given is invalid");
		}
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RecordNotFoundException("Category Not Found With Given Id : "+categoryId));
		
		Product product =productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product Not Found with the given Id " + productId));

		product.setCategory(category);
		
		return ApiResponse.success(mapper.map(productRepository.save(product), ProductResponseDto.class)," Product's Category Updated SuccessFully");
		
	}

	@Override
	public ApiResponse<List<ProductResponseDto>> getAllOfCategory(Long categoryId, int pageNumber, int pageSize,
			String sortBy, String sortDir) {
		
		if(categoryId<=0)
		{
			throw new InvalidArgumentException("CategoryId given is invalid");
		}
		
		
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new RecordNotFoundException("Category Not Found With Given Id : "+categoryId));

		Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Product> page = productRepository.findByCategory(category,pageable);

		PaginationInfo paginationInfo = PaginationInfo.builder().pageNumber(page.getNumber()).pageSize(page.getSize())
				.lastPage(page.isLast()).totalElements(page.getTotalElements()).hasNext(page.hasNext())
				.hasPreviuos(page.hasPrevious()).totalPages(page.getTotalPages()).build();

		List<ProductResponseDto> content = page.getContent().stream().map(c -> mapper.map(c, ProductResponseDto.class))
				.toList();

		return ApiResponse.successWithPagination(content, paginationInfo);
	}

}
