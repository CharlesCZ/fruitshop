package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.mapper.CategoryMapper;
import org.czekalski.fruitshop.api.v1.model.CategoryDTO;
import org.czekalski.fruitshop.domain.Category;
import org.czekalski.fruitshop.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CategoryServiceTest {


    public static final String NAME = "Cytusy";
    public static final long ID = 2L;
    @Mock
    CategoryRepository categoryRepository;

    CategoryService categoryService;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService=new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
        
    }


    @Test
    public void getAllCategoriesTest() {
//given
        List<Category> categories= Arrays.asList(new Category(),new Category(),new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> returnedCategories=  categoryService.getAllCategories();

        //then
        Assert.assertEquals(3,returnedCategories.size());
    }

    @Test
    public void getCategoryByNameTest() {
        //given
        Category namedCategory=new Category();
        namedCategory.setId(ID);
        namedCategory.setName(NAME);

        when(categoryRepository.findByName(NAME)).thenReturn(Optional.of(namedCategory));

//when
        CategoryDTO returnedCategory=categoryService.getCategoryByName(NAME);


        //then
        Assert.assertEquals(NAME,returnedCategory.getName());
    }
}