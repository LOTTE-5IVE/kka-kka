package kkakka.mainservice.product.application;

import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.common.dto.PageInfo;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.elasticsearch.application.dto.CategoryDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.ProductDocumentDto;
import kkakka.mainservice.elasticsearch.application.dto.SearchResultDto;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import kkakka.mainservice.product.application.dto.CategoryDto;
import kkakka.mainservice.product.application.dto.NutritionDto;
import kkakka.mainservice.product.application.dto.ProductDetailDto;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.application.recommend.RecommendStrategy;
import kkakka.mainservice.product.application.recommend.RecommenderFactory;
import kkakka.mainservice.product.application.recommend.strategy.ProductRecommender;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.domain.repository.ProductRepositorySupport;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;
    private final RecommenderFactory recommenderFactory;

    public ProductDetailDto showProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(KkaKkaException::new);
        if (Objects.isNull(product.getNutrition())) {
            return ProductDetailDto.toDtoWithoutNutrition(
                    product,
                    CategoryDto.toDto(product.getCategory()),
                    NutritionDto.createEmptyDto());
        }
        return ProductDetailDto.toDto(
                product,
                CategoryDto.toDto(product.getCategory()),
                NutritionDto.toDto(product.getNutrition())
        );
    }

    public SearchResultResponse showAllProductsWithCategoryAndSearch(
            Optional<Long> categoryId,
            String sortBy,
            Pageable pageable
    ) {
        Page<ProductDocumentDto> productDtos = productRepositorySupport.findAllByCategoryWithSort(
                        categoryId,
                        sortBy,
                        pageable)
                .map(product -> ProductDocumentDto.toDto(
                        product,
                        CategoryDocumentDto.toDto(product.getCategory())
                ));

        return SearchResultDto.toResponseDto(
                productDtos.getTotalElements(),
                productDtos.toList(),
                PageInfo.from(pageable.getPageNumber(),
                        productDtos.getTotalPages(),
                        pageable.getPageSize(),
                        productDtos.getTotalElements())
        );
    }

    public Page<ProductDto> showProductsByRecommendation(LoginMember loginMember,
                                                         Pageable pageable) {
        final ProductRecommender productRecommender = recommenderFactory.get(
                loginMember.getAuthority());
        return productRecommender.recommend(Optional.ofNullable(loginMember.getId()), pageable)
                .map(product -> ProductDto.toDto(
                        product,
                        CategoryDto.toDto(product.getCategory())
                ));
    }

    public Page<ProductDto> showRecommendationByProduct(Long productId, Pageable pageable) {
        final ProductRecommender productRecommender = recommenderFactory.get(
                RecommendStrategy.PRODUCT);
        return productRecommender.recommend(Optional.of(productId), pageable)
                .map(product -> ProductDto.toDto(
                        product,
                        CategoryDto.toDto(product.getCategory())
                ));
    }
}
