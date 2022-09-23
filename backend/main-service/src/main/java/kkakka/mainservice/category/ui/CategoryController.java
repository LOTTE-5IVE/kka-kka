package kkakka.mainservice.category.ui;

import kkakka.mainservice.category.application.CategoryService;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

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
    public ResponseEntity<List<ResponseCategoryProducts>> getCategories(@PathVariable("categoryId") Long categoryId,
                                                                        Pageable pageable) {
        Page<ResponseCategoryProducts> result = categoryService.getProductsByCategoryId(categoryId, pageable);

        System.out.println("전체 페이지 수: " + result.getTotalPages());
        System.out.println("전체 상품목록 수: " + result.getTotalElements());
        System.out.println("현재 페이지(기본0) : " + result.getNumber());
        System.out.println("hasNext: " + result.hasNext());

        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

}
