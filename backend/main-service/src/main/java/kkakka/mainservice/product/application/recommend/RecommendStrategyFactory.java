package kkakka.mainservice.product.application.recommend;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import kkakka.mainservice.member.auth.ui.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendStrategyFactory {

    private static final Map<Authority, ProductRecommender> recommenderFactory = new HashMap<>();

    private final MemberRecommendStrategy memberRecommendStrategy;
    private final AnonymousRecommendStrategy anonymousRecommendStrategy;

    @PostConstruct
    public void init() {
        recommenderFactory.put(Authority.MEMBER, memberRecommendStrategy);
        recommenderFactory.put(Authority.ANONYMOUS, anonymousRecommendStrategy);
    }

    public static ProductRecommender get(Authority authority) {
        return recommenderFactory.get(authority);
    }
}
