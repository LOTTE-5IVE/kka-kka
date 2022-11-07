package kkakka.mainservice.product.application.recommend;

import java.util.Optional;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductRecommendStrategy implements ProductRecommender{

    @Override
    public Page<Product> recommend(Optional<Long> memberId, Pageable pageable) {
        return null;
    }
}
