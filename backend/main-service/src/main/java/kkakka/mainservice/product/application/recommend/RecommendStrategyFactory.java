package kkakka.mainservice.product.application.recommend;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import kkakka.mainservice.common.auth.Authority;
import kkakka.mainservice.product.application.recommend.strategy.AnonymousRecommendStrategy;
import kkakka.mainservice.product.application.recommend.strategy.MemberRecommendStrategy;
import kkakka.mainservice.product.application.recommend.strategy.ProductRecommendStrategy;
import kkakka.mainservice.product.application.recommend.strategy.ProductRecommender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class RecommendStrategyFactory implements RecommenderFactory {

    private static final Map<RecommendStrategy, ProductRecommender> recommenderFactory = new HashMap<>();

    private final MemberRecommendStrategy memberRecommendStrategy;
    private final AnonymousRecommendStrategy anonymousRecommendStrategy;
    private final ProductRecommendStrategy productRecommendStrategy;

    @PostConstruct
    public void init() {
        recommenderFactory.put(RecommendStrategy.MEMBER, memberRecommendStrategy);
        recommenderFactory.put(RecommendStrategy.ANONYMOUS, anonymousRecommendStrategy);
        recommenderFactory.put(RecommendStrategy.PRODUCT, productRecommendStrategy);
    }

    public ProductRecommender get(Authority authority) {
        return recommenderFactory.get(RecommendStrategy.valueOf(authority.name()));
    }

    public ProductRecommender get(RecommendStrategy strategy) {
        return recommenderFactory.get(strategy);
    }
}
