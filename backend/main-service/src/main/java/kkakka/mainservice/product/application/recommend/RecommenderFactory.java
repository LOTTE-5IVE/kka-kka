package kkakka.mainservice.product.application.recommend;

import kkakka.mainservice.member.auth.ui.Authority;

public interface RecommenderFactory {

    ProductRecommender get(Authority authority);
}
