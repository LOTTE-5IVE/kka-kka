package kkakka.mainservice.product.ui;

import java.util.List;
import java.util.Optional;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.dto.PageableResponse;
import kkakka.mainservice.elasticsearch.application.ProductDocumentService;
import kkakka.mainservice.elasticsearch.ui.dto.SearchParamRequest;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.product.application.ProductService;
import kkakka.mainservice.product.application.dto.ProductDetailDto;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.ui.dto.ProductDetailResponse;
import kkakka.mainservice.product.ui.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductDocumentService productDocumentService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in Product Service";
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> showProductDetail(
            @PathVariable("productId") Long productId) {
        ProductDetailDto productDetailDto = productService.showProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDto.toResponseDto());
    }

    @GetMapping
    public ResponseEntity<SearchResultResponse> showAllProducts(
        @ModelAttribute SearchParamRequest searchParamRequest,
        Pageable pageable) {
        SearchResultResponse result;
        if (searchParamRequest.isKeyword()) {
            result = productDocumentService.findByKeyword(
                searchParamRequest.toDto(),
                pageable
            );
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        result = productService.showAllProductsWithCategoryAndSearch(
            Optional.ofNullable(searchParamRequest.getCategory()),
            searchParamRequest.getSortBy(),
            pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/recommend")
    public ResponseEntity<PageableResponse<List<ProductResponse>>> showProductsByRecommendation(
            @AuthenticationPrincipal LoginMember loginMember,
            @PageableDefault(size = 9) Pageable pageable
    ) {
        final Page<ProductDto> productDtos = productService.showProductsByRecommendation(
                loginMember,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(dtoToPageableResponse(pageable, productDtos));
    }

    private PageableResponse<List<ProductResponse>> dtoToPageableResponse(Pageable pageable,
            Page<ProductDto> productDtos) {

        final List<ProductResponse> response = productDtos
                .map(ProductDto::toResponseDto)
                .getContent();

        final PageInfo pageInfo = PageInfo.from(
                pageable.getPageNumber(),
                productDtos.getTotalPages(),
                pageable.getPageSize(),
                productDtos.getTotalElements()
        );

        return PageableResponse.from(response, pageInfo);
    }
}
