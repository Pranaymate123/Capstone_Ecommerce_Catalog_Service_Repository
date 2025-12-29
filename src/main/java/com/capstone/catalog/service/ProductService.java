package com.capstone.catalog.service;

import java.util.List;

import com.capstone.catalog.dto.request.ProductRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.ProductResponseDto;

public interface ProductService {

	 //create
    ApiResponse<ProductResponseDto> createProductWithCategory(ProductRequestDto requestDto,Long id);

    ApiResponse<ProductResponseDto> getProductById(Long id);
    
    ApiResponse<List<ProductResponseDto>> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    
    ApiResponse<Void> deleteProduct(Long id);
    
  
    ApiResponse<ProductResponseDto> updateProductById(Long id,ProductRequestDto requestDto);
    
//    //get all : live
    ApiResponse<List<ProductResponseDto >> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

//    //search products by name 
    ApiResponse<List<ProductResponseDto>> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);


//   update category of product
    ApiResponse<ProductResponseDto> updateCategory(Long productId,Long categoryId);

//   Get All products Of Category
    ApiResponse<List<ProductResponseDto>> getAllOfCategory(Long categoryId,int pageNumber,int pageSize,String sortBy, String sortDir);
    
}
