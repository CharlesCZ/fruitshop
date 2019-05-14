package org.czekalski.fruitshop.controllers.v1;


import io.swagger.annotations.ApiOperation;
import org.czekalski.fruitshop.api.v1.model.CategoryDTO;
import org.czekalski.fruitshop.api.v1.model.CategoryListDTO;
import org.czekalski.fruitshop.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value ="get list of All categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories(){
        return new CategoryListDTO(categoryService.getAllCategories());
}

@GetMapping("{name}")
@ResponseStatus(HttpStatus.OK)
   public CategoryDTO getCategoryByName(@PathVariable  String name){

        return categoryService.getCategoryByName(name);
   }

}
