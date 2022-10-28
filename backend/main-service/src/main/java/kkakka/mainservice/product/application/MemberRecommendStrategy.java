package kkakka.mainservice.product.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRecommendStrategy implements ProductRecommender {

    private static final int RECENT_ORDER_LIMIT = 5;

    // TODO: auth server 분리 반영 후 common 에 bean 등록 필요
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<Product> recommend(Optional<Long> memberId, Pageable pageable) {
        final Member member = validateMember(Optional.of(1L));

        /*
            TODO: 추천 서버에 요청
                1. 구매한 상품 중 평가를 높게 남긴 상품
                2. 최근 구매 상품
        */

        final List<Review> topRatedReview = reviewRepository.findTopRatingReviewByMemberId(
                member.getId(), Pageable.ofSize(1));

        if (topRatedReview.isEmpty()) {
            // TODO: 그냥 최근 주문한 상품 중에서 주기

            final List<Order> orders = orderRepository.findAllByMemberId(member.getId(),
                    Pageable.ofSize(RECENT_ORDER_LIMIT));
            // TODO: 아직 주문한 적 없다면 best 추천

            final List<ProductOrder> productOrders = orders.stream()
                    .flatMap(order -> order.getProductOrders().stream())
                    .collect(Collectors.toList());

            final List<Product> orderedProducts = orders.stream()
                    .flatMap(order -> order.getProductOrders().stream())
                    .map(ProductOrder::getProduct)
                    .distinct()
                    .collect(Collectors.toList());
        }

        final Product pivotProduct = topRatedReview.get(0).getProductOrder().getProduct();
        final ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8000/recommendation/" + pivotProduct.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        try {
            final List<RecommendProductDto> recommendProductDtos = objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<RecommendProductDto>>() {
                    }
            );
            final List<Long> productIds = recommendProductDtos.stream()
                    .map(RecommendProductDto::getId)
                    .collect(Collectors.toList())
                    .subList(0, pageable.getPageSize());

            return new PageImpl<>(productRepository.findAllById(productIds));
        } catch (Exception e) {
            throw new KkaKkaException();
        }
    }

    private Member validateMember(Optional<Long> memberId) {
        memberId.orElseThrow(NotFoundMemberException::new);
        return memberRepository.findById(memberId.get())
                .orElseThrow(NotFoundMemberException::new);
    }
}
