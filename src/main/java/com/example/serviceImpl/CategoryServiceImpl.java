package com.example.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.example.entity.Category;
import com.example.dto.CategoryDto;
import com.example.dto.CategoryResponse;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {

		Category category = mapper.map(categoryDto, Category.class);

		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepo.findByIsDeletedFalse();

		List<CategoryDto> categoriesDtoList = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class))
				.toList();
		return categoriesDtoList;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryList = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class))
				.toList();

		return categoryList;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);

		if (findByCategory.isPresent()) {
			Category category = findByCategory.get();

			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findById(id);

		if (findByCategory.isPresent()) {
			Category category = findByCategory.get();

			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}
}
