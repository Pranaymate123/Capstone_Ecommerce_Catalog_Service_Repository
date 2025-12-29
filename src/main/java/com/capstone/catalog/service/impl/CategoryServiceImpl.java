package com.capstone.catalog.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.catalog.dto.request.CategoryRequestDto;
import com.capstone.catalog.dto.response.ApiResponse;
import com.capstone.catalog.dto.response.CategoryResponseDto;
import com.capstone.catalog.dto.response.PaginationInfo;
import com.capstone.catalog.entities.Category;
import com.capstone.catalog.exceptions.InvalidArgumentException;
import com.capstone.catalog.exceptions.RecordNotFoundException;
import com.capstone.catalog.repository.CategoryRepository;
import com.capstone.catalog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ApiResponse<CategoryResponseDto> create(CategoryRequestDto requestDto) {
		Category category = mapper.map(requestDto, Category.class);

		//Generate the unique Serial No. 
		String categoryId = UUID.randomUUID().toString();
		
		category.setCategoryId(categoryId);
		Category savedCategory = categoryRepository.save(category);

		return ApiResponse.success(mapper.map(savedCategory, CategoryResponseDto.class), "Category Created SuccessFully ");
	}

	@Override
	public ApiResponse<CategoryResponseDto>  getById(Long id) {
		
		if(id<=0)
		{
			throw new InvalidArgumentException("Id given is invalid");
		}
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Category not found with id : " + id));

		return ApiResponse.success(mapper.map(category, CategoryResponseDto.class), "Category Fetched SuccessFully ");
	}

	@Override
	public ApiResponse<List<CategoryResponseDto>> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? (Sort.by(sortBy).ascending()) : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Category> page = categoryRepository.findAll(pageable);
		
		
		PaginationInfo paginationInfo = PaginationInfo.builder()
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.lastPage(page.isLast())
				.totalElements(page.getTotalElements())
				.hasNext(page.hasNext())
				.hasPreviuos(page.hasPrevious())
				.totalPages(page.getTotalPages())
				.build();
		
		List<CategoryResponseDto> content = page.getContent().stream()
				.map(c->mapper.map(c, CategoryResponseDto.class))
				.toList();
		
		return 	ApiResponse.successWithPagination(content,paginationInfo); 
	}

	@Override
	public ApiResponse<Void> deleteCategory(Long id) {
		// TODO Auto-generated method stub
		
		if(id<=0)
		{
			throw new InvalidArgumentException("Id given is invalid");
		}

		Boolean flag = categoryRepository.existsById(id);

		if (!flag) {
			throw new RecordNotFoundException("Category not found with id : " + id);
		}
		categoryRepository.deleteById(id);
		return ApiResponse.success("Category Deleted SuccessFully");
	}

	@Override
	public ApiResponse<CategoryResponseDto> updateCategory(CategoryRequestDto requestDto, Long id) {
		
		if(id<=0)
		{
			throw new InvalidArgumentException("Id given is invalid");
		}
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Category not found with id : " + id));

		if (requestDto.getTitle() != null && !requestDto.getTitle().trim().isEmpty()) {
			category.setTitle(requestDto.getTitle());
		}

		if (requestDto.getDescription() != null && !requestDto.getDescription().trim().isEmpty())
			category.setDescription(requestDto.getDescription());

		if (requestDto.getCategoryImageUrl() != null && !requestDto.getCategoryImageUrl().trim().isEmpty()) {
			category.setCategoryImageUrl(requestDto.getCategoryImageUrl());
		}

		return ApiResponse.success(mapper.map(categoryRepository.save(category), CategoryResponseDto.class), "Category Updated SuccessFully ");

	}

}
