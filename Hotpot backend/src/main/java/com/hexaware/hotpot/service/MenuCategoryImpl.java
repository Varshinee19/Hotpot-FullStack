package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Implementation Class.
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.MenuCategoriesDto;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.exception.CategoryDoesNotExistException;
import com.hexaware.hotpot.repository.MenuCategoriesRepository;
@Service
public class MenuCategoryImpl implements IMenuCategoryService {
	@Autowired
	MenuCategoriesRepository repo;
	

	@Override
	public MenuCategories addCategory(MenuCategoriesDto category) {
		// TODO Auto-generated method stub
		MenuCategories categories=new MenuCategories();
		categories.setCategoryName(category.getCategoryName());
		return repo.save(categories);
		
	}

	@Override
	public MenuCategories updateCategory(Integer categoryId,MenuCategoriesDto category)throws CategoryDoesNotExistException{
		// TODO Auto-generated method stub
		MenuCategories categories=repo.findById(categoryId).orElse(null);
		categories.setCategoryName(category.getCategoryName());
		return repo.save(categories);
	}
	@Override
	public MenuCategories getById(int categoryId)throws CategoryDoesNotExistException {
		// TODO Auto-generated method stub
		return repo.findById(categoryId).orElseThrow(()->new CategoryDoesNotExistException());
	}


	@Override
	public List<MenuCategories> getAllCategories() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public String deleteCategory(int categoryId) throws CategoryDoesNotExistException {
		
		repo.findById(categoryId).orElseThrow(()->new CategoryDoesNotExistException());
		repo.deleteById(categoryId);
		return "record deleted";
	}

	@Override
	public MenuCategories getByName(String categoryName)throws CategoryDoesNotExistException {
		// TODO Auto-generated method stub
		return repo.findByCategoryName(categoryName).orElseThrow(()->new  CategoryDoesNotExistException());
	}


}
