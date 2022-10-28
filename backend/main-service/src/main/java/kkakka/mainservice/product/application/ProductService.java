package kkakka.mainservice.product.application;

import java.util.Optional;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.product.application.dto.CategoryDto;
import kkakka.mainservice.product.application.dto.NutritionDto;
import kkakka.mainservice.product.application.dto.ProductDetailDto;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.SearchWords;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.domain.repository.ProductRepositorySupport;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRepositorySupport productRepositorySupport;

    public ProductDetailDto showProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(KkaKkaException::new);
        return ProductDetailDto.toDto(
                product,
                CategoryDto.toDto(product.getCategory()),
                NutritionDto.toDto(product.getNutrition())
        );
    }

    public Page<ProductDto> showAllProductsWithCategoryAndSearch(
            Optional<Long> categoryId,
            String sortBy,
            String keyword,
            Pageable pageable
    ) {
        final SearchWords searchWords = SearchWords.create(keyword);
        return productRepositorySupport.findAllByCategoryWithSort(categoryId, sortBy, searchWords,
                        pageable)
                .map(product -> ProductDto.toDto(
                                product,
                                CategoryDto.toDto(product.getCategory())
                        )
                );
    }

    public Page<ProductDto> showProductsByRecommendation(LoginMember loginMember,
            Pageable pageable) {
        final ProductRecommender productRecommender = RecommendStrategyFactory.get(
                loginMember.getAuthority());

        return productRecommender.recommend(Optional.ofNullable(loginMember.getId()), pageable)
                .map(product -> ProductDto.toDto(
                        product,
                        CategoryDto.toDto(product.getCategory())
                ));
    }
}
