package com.hexaware.hotpot.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.MenuCategoriesDto;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.exception.CategoryDoesNotExistException;
import com.hexaware.hotpot.repository.MenuCategoriesRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class MenuCategoryServiceImplTest {
    @Autowired
    private IMenuCategoryService service;

    @Autowired
    private MenuCategoriesRepository repo;

    private MenuCategoriesDto categoryDto;

    @BeforeEach
    void setUp() {
        // Clean DB before each test
        repo.deleteAll();

        categoryDto = new MenuCategoriesDto();
        categoryDto.setCategoryName("Dinner");
    }

    @Test
    void testAddCategory() {
        MenuCategories saved = service.addCategory(categoryDto);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Dinner", saved.getCategoryName());
        Assertions.assertTrue(saved.getCategoryId() > 0);
    }

    @Test
    void testUpdateCategory() throws CategoryDoesNotExistException {
        MenuCategories category = service.addCategory(categoryDto);
        MenuCategoriesDto updatedDto = new MenuCategoriesDto();
        updatedDto.setCategoryName("Brunch");

        MenuCategories updated = service.updateCategory(category.getCategoryId(), updatedDto);

        Assertions.assertEquals("Brunch", updated.getCategoryName());
    }

    @Test
    void testGetById() throws CategoryDoesNotExistException {
        MenuCategories category = service.addCategory(categoryDto);
        MenuCategories found = service.getById(category.getCategoryId());

        Assertions.assertEquals(category.getCategoryId(), found.getCategoryId());
        Assertions.assertEquals("Dinner", found.getCategoryName());
    }

    @Test
    void testGetAllCategories() {
        service.addCategory(categoryDto);
        service.addCategory(new MenuCategoriesDto() {{ setCategoryName("Breakfast"); }});

        List<MenuCategories> all = service.getAllCategories();
        Assertions.assertEquals(2, all.size());
    }

    @Test
    void testDeleteCategory() throws CategoryDoesNotExistException {
        MenuCategories category = service.addCategory(categoryDto);

        String result = service.deleteCategory(category.getCategoryId());
        Assertions.assertEquals("record deleted", result);
        Assertions.assertEquals(0, repo.count());
    }

    @Test
    void testDeleteCategoryNotFound() {
        Assertions.assertThrows(CategoryDoesNotExistException.class, () -> service.deleteCategory(999));
    }
	
	

}
