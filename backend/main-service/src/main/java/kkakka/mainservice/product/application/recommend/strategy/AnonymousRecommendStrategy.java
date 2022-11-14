package kkakka.mainservice.product.application.recommend.strategy;

import java.util.Optional;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnonymousRecommendStrategy implements ProductRecommender {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> recommend(Optional<Long> recommendPivotId, Pageable pageable) {
        return productRepository.findAllOrderByRatingAvg(pageable);
    }
}
