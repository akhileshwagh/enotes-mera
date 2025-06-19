package com.example.service;

import java.util.List;

import com.example.dto.CategoryDto;
import com.example.dto.CategoryResponse;

public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

}
  