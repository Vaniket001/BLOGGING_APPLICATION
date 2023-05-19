package com.lcwd.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.blog.entity.Category;
import com.lcwd.blog.exception.ResourceNotFoundException;
import com.lcwd.blog.payloads.CategoryDto;
import com.lcwd.blog.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory=this.categoryRepository.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedcategory=this.categoryRepository.save(category);
		return this.modelMapper.map(updatedcategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
	Category category=this.categoryRepository.findById(categoryId)
		.orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories=this.categoryRepository.findAll();
		List<CategoryDto> categoryDtos=categories.stream()
				.map(category->this.modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category","categoryId", categoryId));
		this.categoryRepository.delete(category);
	}

}
