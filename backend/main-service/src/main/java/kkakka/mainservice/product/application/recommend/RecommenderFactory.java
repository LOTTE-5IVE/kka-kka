package kkakka.mainservice.product.application.recommend;

import kkakka.mainservice.member.auth.ui.Authority;
import kkakka.mainservice.product.application.recommend.strategy.ProductRecommender;

public interface RecommenderFactory {

    ProductRecommender get(Authority authority);

    ProductRecommender get(RecommendStrategy recommendStrategy);
}
