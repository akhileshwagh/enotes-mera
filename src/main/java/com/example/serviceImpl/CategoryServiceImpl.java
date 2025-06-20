package com.example.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.example.entity.Category;
import com.example.exception.ResourceNotFoundException;
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

		if (ObjectUtils.isEmpty(category.getId())) {

			category.setIsDeleted(false);
			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
		} else {
			updateCategory(category);
		}
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepo.findById(category.getId());
		if (findById.isPresent()) {
			Category existingCategory = findById.get();
			category.setCreatedBy(existingCategory.getCreatedBy());
			category.setCreatedOn(existingCategory.getCreatedOn());
			category.setIsDeleted(existingCategory.getIsDeleted());

			category.setUpdatedBy(1);
			category.setUpdatedOn(new Date());
		}
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
	public CategoryDto getCategoryById(Integer id) throws Exception {
		Category category = categoryRepo.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + id));

		if (!ObjectUtils.isEmpty(category)) {
//			if (category.getName() == null) {
//				throw new IllegalArgumentException("name is null");
//			}
			//category.getName().toLowerCase();
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
