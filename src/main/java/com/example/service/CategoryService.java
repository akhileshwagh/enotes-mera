package com.example.service;

import java.util.List;

import com.example.entity.Category;

public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();

}
