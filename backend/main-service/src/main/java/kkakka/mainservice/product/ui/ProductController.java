package kkakka.mainservice.product.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.dto.PageableResponse;
import kkakka.mainservice.product.application.ProductService;
import kkakka.mainservice.product.application.dto.ProductDetailDto;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.ui.dto.NutritionResponse;
import kkakka.mainservice.product.ui.dto.ProductCategoryResponse;
import kkakka.mainservice.product.ui.dto.ProductDetailResponse;
import kkakka.mainservice.product.ui.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> showProductDetail(
            @PathVariable("productId") Long productId) {
        ProductDetailDto productDetailDto = productService.showProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ProductDetailResponse(
                        productDetailDto.getId(),
                        new ProductCategoryResponse(productDetailDto.getCategoryId(),
                                productDetailDto.getCategoryName()),
                        productDetailDto.getName(),
                        productDetailDto.getPrice(),
                        productDetailDto.getStock(),
                        productDetailDto.getImageUrl(),
                        productDetailDto.getDetailImageUrl(),
                        productDetailDto.getNutritionInfoUrl(),
                        productDetailDto.getDiscount(),
                        new NutritionResponse(
                                productDetailDto.getNutritionId(),
                                productDetailDto.getCalorie(),
                                productDetailDto.getCarbohydrate(),
                                productDetailDto.getSugar(),
                                productDetailDto.getProtein(),
                                productDetailDto.getFat(),
                                productDetailDto.getSaturatedFat(),
                                productDetailDto.getTransFat(),
                                productDetailDto.getCholesterol(),
                                productDetailDto.getSodium(),
                                productDetailDto.getCalcium()
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<PageableResponse<List<ProductResponse>>> showAllProducts(
            @RequestParam(value = "category", required = false) Long categoryId,
            Pageable pageable) {
        final Page<ProductDto> productDtos = productService.showAllProductsWithCategory(
                Optional.ofNullable(categoryId), pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtoToPageableResponse(pageable, productDtos));
    }

    @GetMapping("/search")
    public ResponseEntity<PageableResponse<List<ProductResponse>>> showProductsBySearch(
            @RequestParam(value = "keyword", required = false) String keyword,
            Pageable pageable
    ) {
        if (Optional.ofNullable(keyword).isEmpty()) {
            final Page<ProductDto> productDtos = productService.showAllProductsWithCategory(
                    Optional.empty(), pageable);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(dtoToPageableResponse(pageable, productDtos));
        }

        Page<ProductDto> productDtos = productService.showProductsBySearch(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtoToPageableResponse(pageable, productDtos));
    }

    private PageableResponse<List<ProductResponse>> dtoToPageableResponse(Pageable pageable,
            Page<ProductDto> productDtos) {

        final List<ProductResponse> response = productDtos
                .map(productDto -> new ProductResponse(
                        productDto.getId(),
                        new ProductCategoryResponse(productDto.getCategoryId(),
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

        return PageableResponse.from(response, pageInfo);
    }
}
