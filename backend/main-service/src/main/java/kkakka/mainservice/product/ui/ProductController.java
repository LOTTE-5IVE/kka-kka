package kkakka.mainservice.product.ui;

import java.util.List;
import kkakka.mainservice.category.application.CategoryService;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.product.application.ProductService;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import kkakka.mainservice.product.ui.dto.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService
        , CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> showProductDetail(
        @PathVariable("productId") Long productId) {

        ProductResponseDto responseDto = productService.getProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponsePageDto> showCategoryProducts(
        @RequestParam("category") Long categoryId, Pageable pageable) {

        ResponsePageDto response = categoryService.getProductsByCategoryId(categoryId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponsePageDto> showALlOfProducts() {

        ResponsePageDto response = productService.getProductByRand();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/search")
    public ResponseEntity showProductsBySearch(@RequestBody SearchRequest searchRequest) {

        List<ProductResponseDto> productResponseDtos = productService.showProductsBySearch(
            searchRequest.toDto());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtos);
    }
}
