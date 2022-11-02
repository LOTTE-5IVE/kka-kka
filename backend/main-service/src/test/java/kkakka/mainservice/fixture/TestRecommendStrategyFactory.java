package kkakka.mainservice.fixture;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_3;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_4;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_5;

import java.util.List;
import java.util.Optional;
import kkakka.mainservice.member.auth.ui.Authority;
import kkakka.mainservice.product.application.recommend.ProductRecommender;
import kkakka.mainservice.product.application.recommend.RecommenderFactory;
import kkakka.mainservice.product.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestRecommendStrategyFactory implements RecommenderFactory {

    @Override
    public ProductRecommender get(Authority authority) {
        return new ProductRecommender() {
            @Override
            public Page<Product> recommend(Optional<Long> memberId, Pageable pageable) {
                return new PageImpl<>(
                        List.of(PRODUCT_3, PRODUCT_4, PRODUCT_5, PRODUCT_1, PRODUCT_2));
            }
        };
    }
}
