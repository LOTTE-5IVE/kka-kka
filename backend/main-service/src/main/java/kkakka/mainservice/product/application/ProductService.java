package kkakka.mainservice.product.application;

import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.common.exception.KkaKkaException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
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

    public ResponsePageDto getProductByRand() {
        Long qty = productRepository.countBy();
        int idx = (int) ((Math.random() * qty) / 10);
        Page<Product> randomProducts = productRepository.findAll(PageRequest.of(idx, 10));

        return ResponsePageDto.from(
                randomProducts,
                randomProducts.getContent().stream()
                        .map(ResponseCategoryProducts::from)
                        .collect(Collectors.toList())
        );
    }

    public Page<ProductDto> showAllProductsWithCategory(Optional<Long> categoryId,
            Pageable pageable) {
        if (categoryIsEmpty(categoryId)) {
            return productRepository.findAll(pageable)
                    .map(product -> ProductDto.toDto(
                                    product,
                                    CategoryDto.toDto(product.getCategory())
                            )
                    );
        }

        return showAllProductsByCategory(categoryId.get(), pageable);
    }

    public Page<ProductDto> showProductsBySearch(String keyword, Pageable pageable) {
        SearchWords searchWords = SearchWords.create(keyword);

        final Page<Product> products = productRepositorySupport.findBySearch(searchWords, pageable);

        return products.map(product -> ProductDto.toDto(
                product,
                CategoryDto.toDto(product.getCategory())
        ));
    }

    private Page<ProductDto> showAllProductsByCategory(Long categoryId,
            Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(product -> ProductDto.toDto(
                                product,
                                CategoryDto.toDto(product.getCategory())
                        )
                );
    }

    private boolean categoryIsEmpty(Optional<Long> categoryId) {
        if (categoryId.isEmpty()) {
            return true;
        }
        return categoryRepository.findById(categoryId.get())
                .isEmpty();
    }
}
