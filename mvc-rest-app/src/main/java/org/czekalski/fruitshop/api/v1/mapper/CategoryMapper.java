package org.czekalski.fruitshop.api.v1.mapper;

import org.czekalski.fruitshop.api.v1.model.CategoryDTO;
import org.czekalski.fruitshop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE= Mappers.getMapper(CategoryMapper.class);


  
    CategoryDTO categoryToCategoryDTO(Category category);


}
