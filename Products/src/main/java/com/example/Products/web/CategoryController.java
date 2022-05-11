package com.example.Products.web;

import com.example.Products.model.views.CategoryViewModel;
import com.example.Products.services.CategoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper mapper;

    public CategoryController(CategoryService categoryService, ModelMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @GetMapping
    @ApiOperation(httpMethod = "GET", value = "Get all category", notes = "Information on existing categories and all products available in them", response = CategoryViewModel.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity<List<CategoryViewModel>> getAllCategory() {
        return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
    }
}
