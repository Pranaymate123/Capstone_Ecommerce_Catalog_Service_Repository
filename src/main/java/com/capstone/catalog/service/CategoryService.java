package com.capstone.catalog.service;

import java.util.List;

import com.capstone.catalog.dto.request.CategoryRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.CategoryResponseDto;

public interface CategoryService {
	
	public ApiResponse<CategoryResponseDto> create(CategoryRequestDto requestDto);
	
	public ApiResponse<CategoryResponseDto>  getById(Long id);
	
	ApiResponse<List<CategoryResponseDto>> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	public ApiResponse<Void> deleteCategory(Long id);
	
	public ApiResponse<CategoryResponseDto> updateCategory(CategoryRequestDto requestDto , Long id);
}
