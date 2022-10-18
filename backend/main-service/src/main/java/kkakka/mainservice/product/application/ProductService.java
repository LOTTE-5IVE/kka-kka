package kkakka.mainservice.product.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.product.SearchDto;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.application.dto.ProductDto.CategoryDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.SearchWords;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
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

    public ProductDto showProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(KkaKkaException::new);
        return new ProductDto(
                product.getId(),
                new CategoryDto(product.getCategoryId(), product.getCategoryName()),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getDetailImageUrl(),
                product.getNutritionInfoUrl(),
                product.getDiscount());
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
                    .map(product -> new ProductDto(
                            product.getId(),
                            new CategoryDto(product.getCategoryId(), product.getCategoryName()),
                            product.getName(),
                            product.getPrice(),
                            product.getStock(),
                            product.getImageUrl(),
                            product.getDetailImageUrl(),
                            product.getNutritionInfoUrl(),
                            product.getDiscount()
                    ));
        }
        return showAllProductsByCategory(categoryId.get(), pageable);
    }

    public List<ProductResponseDto> showProductsBySearch(SearchDto searchDto) {

        String keyword = searchDto.getKeyword();
        SearchWords searchWords = SearchWords.create(keyword);
        List<String> getSearchWords = searchWords.getSearchWords();
        List<Product> products = new ArrayList<>();

        for (String searchWord : getSearchWords) {
            products.addAll(productRepository.findByCategory(searchWord));
        }
        for (String searchWord : getSearchWords) {
            products.addAll(productRepository.findByName(searchWord));
        }

        return products.stream()
                .distinct()
                .map(ProductResponseDto::create)
                .collect(Collectors.toList());
    }

    private Page<ProductDto> showAllProductsByCategory(Long categoryId,
            Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(product -> new ProductDto(
                        product.getId(),
                        new CategoryDto(product.getCategoryId(), product.getCategoryName()),
                        product.getName(),
                        product.getPrice(),
                        product.getStock(),
                        product.getImageUrl(),
                        product.getDetailImageUrl(),
                        product.getNutritionInfoUrl(),
                        product.getDiscount()
                ));
    }

    private boolean categoryIsEmpty(Optional<Long> categoryId) {
        if (categoryId.isEmpty()) {
            return true;
        }
        return categoryRepository.findById(categoryId.get())
                .isEmpty();
    }
}
