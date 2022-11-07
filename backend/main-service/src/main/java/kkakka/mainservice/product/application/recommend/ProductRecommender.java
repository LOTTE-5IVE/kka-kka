package kkakka.mainservice.product.application.recommend;

import java.util.Optional;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRecommender {

    Page<Product> recommend(Optional<Long> recommendPivotId, Pageable pageable);
}
