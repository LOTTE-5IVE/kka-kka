package kkakka.mainservice.category.ui;

import kkakka.mainservice.category.application.CategoryService;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategory;
import kkakka.mainservice.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryRepository categoryRepository;
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Category Service";
    }

    @PostConstruct
    public void init() {
        categoryRepository.save(new Category("비스킷/샌드"));
        categoryRepository.save(new Category("스낵/봉지과자"));
        categoryRepository.save(new Category("박스과자"));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ResponseCategory>> getCategories(@PathVariable("categoryId") Long categoryId) {
        List<ResponseCategory> result = categoryService.getProductsByCategoryId(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
