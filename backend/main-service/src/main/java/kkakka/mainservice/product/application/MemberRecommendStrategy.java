package kkakka.mainservice.product.application;

import java.util.Optional;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class MemberRecommendStrategy implements ProductRecommender {

    @Override
    public Page<Product> recommend(Optional<Long> memberId, Pageable pageable) {
        // TODO: 회원 검증
        /*
            TODO: 추천 서버에 요청
            - 최근 구매 상품 or 최근 후기 남긴 상품
            - 구매한 상품 중 평가를 높게 남긴 상품
        */
        return null;
    }
}
