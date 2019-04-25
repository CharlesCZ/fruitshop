package org.czekalski.fruitshop.services;

import org.czekalski.fruitshop.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryByName(String name);

}
