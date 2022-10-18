package kkakka.mainservice.product.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.category.application.CategoryService;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.dto.PageableResponse;
import kkakka.mainservice.product.application.ProductService;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import kkakka.mainservice.product.ui.dto.ProductResponseDto.CategoryResponse;
import kkakka.mainservice.product.ui.dto.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> showProductDetail(
            @PathVariable("productId") Long productId) {
        ProductDto productDto = productService.showProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ProductResponseDto(
                        productDto.getId(),
                        new CategoryResponse(productDto.getCategoryId(),
                                productDto.getCategoryName()),
                        productDto.getName(),
                        productDto.getPrice(),
                        productDto.getStock(),
                        productDto.getImageUrl(),
                        productDto.getDetailImageUrl(),
                        productDto.getNutritionInfoUrl(),
                        productDto.getDiscount()
                )
        );
    }

    @GetMapping
    public ResponseEntity<PageableResponse<List<ProductResponseDto>>> showAllProducts(
            @RequestParam(value = "category", required = false) Long categoryId,
            Pageable pageable) {
        final Page<ProductDto> productDtos = productService.showAllProductsWithCategory(
                Optional.ofNullable(categoryId), pageable);

        final List<ProductResponseDto> response = productDtos
                .map(productDto -> new ProductResponseDto(
                        productDto.getId(),
                        new CategoryResponse(productDto.getCategoryId(),
                                productDto.getCategoryName()),
                        productDto.getName(),
                        productDto.getPrice(),
                        productDto.getStock(),
                        productDto.getImageUrl(),
                        productDto.getDetailImageUrl(),
                        productDto.getNutritionInfoUrl(),
                        productDto.getDiscount()
                )).stream().collect(Collectors.toList());

        final PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                productDtos.getTotalPages(),
                pageable.getPageSize(),
                productDtos.getTotalElements()
        );

        return ResponseEntity.status(HttpStatus.OK).body(PageableResponse.from(response, pageInfo));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> showProductsBySearch(
            @RequestBody SearchRequest searchRequest
    ) {
        List<ProductResponseDto> productResponseDtos = productService.showProductsBySearch(
                searchRequest.toDto());

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtos);
    }
}
