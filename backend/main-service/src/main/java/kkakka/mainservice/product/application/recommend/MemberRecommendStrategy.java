package kkakka.mainservice.product.application.recommend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import kkakka.mainservice.common.exception.InvalidRecommendResponseException;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.product.application.recommend.dto.RecommendProductDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
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
@Profile("!test")
public class MemberRecommendStrategy implements ProductRecommender {

    @Value("${recommend.request-url}")
    private String RECOMMENDATION_SERVER_URL;

    private static final Random RANDOM = new Random();
    private static final int RECENT_ORDER_LIMIT = 3;
    private static final int DEFAULT_PAGE_SIZE = 9;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<Product> recommend(Optional<Long> recommendPivotId, Pageable pageable) {
        final Member member = validateMember(recommendPivotId);

        final List<Review> highRatedReviews = reviewRepository.findTopRatingReviewByMemberId(
                member.getId(), Pageable.ofSize(5));

        if (highRatedReviews.isEmpty()) {
            final List<Order> orders = orderRepository.findAllByMemberId(member.getId(),
                    Pageable.ofSize(RECENT_ORDER_LIMIT));

            if (orders.isEmpty()) {
                return recommendWithTopRated(pageable);
            }
            return recommendWithRecentOrderRandom(pageable, orders);
        }

        return recommendWithHighRatedReviewRandom(pageable, highRatedReviews);
    }

    private Page<Product> recommendWithRecentOrderRandom(Pageable pageable, List<Order> orders) {
        final List<Product> orderedProducts = orders.stream()
                .flatMap(order -> order.getProductOrders().stream())
                .map(ProductOrder::getProduct)
                .distinct()
                .collect(Collectors.toList());

        final int randomIdx = selectRandomPivotIndex(orderedProducts.size());

        final Product pivotProduct = orderedProducts.get(randomIdx);
        final RecommendProductDto recommendProductDto = requestRecommendation(pivotProduct);

        return findRecommendedProducts(pageable, recommendProductDto);
    }

    private Page<Product> recommendWithHighRatedReviewRandom(Pageable pageable, List<Review> topRatedReview) {
        final Product pivotProduct = reviewedPivotProduct(topRatedReview);
        final RecommendProductDto recommendProductDto = requestRecommendation(pivotProduct);

        return findRecommendedProducts(pageable, recommendProductDto);
    }

    private Product reviewedPivotProduct(List<Review> topRatedReview) {
        final List<Product> reviewedProducts = topRatedReview.stream()
                .map(Review::getProductOrder)
                .map(ProductOrder::getProduct)
                .collect(Collectors.toList());
        return reviewedProducts.get(
                selectRandomPivotIndex(reviewedProducts.size()));
    }

    private List<Product> findMoreProductsByRatingAvg(List<Long> productIds, int numberRequired) {
        return productRepository.findAllOrderByRatingAvg(
                productIds,
                Pageable.ofSize(numberRequired)
        ).getContent();
    }

    private RecommendProductDto requestRecommendation(Product pivotProduct) {
        final ResponseEntity<String> response = restTemplate.exchange(
                RECOMMENDATION_SERVER_URL + pivotProduct.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        return convertResponseBody(response);
    }

    private int selectRandomPivotIndex(int itemSize) {
        return RANDOM.nextInt(itemSize);
    }

    private Page<Product> recommendWithTopRated(Pageable pageable) {
        return productRepository.findAllOrderByRatingAvg(pageable);
    }

    private PageImpl<Product> findRecommendedProducts(
            Pageable pageable,
            RecommendProductDto recommendProductDto
    ) {
        final List<Long> productIds = recommendProductDto.getProductIds();
        final List<Long> pagedProductIds = productIds.subList(
                (int) pageable.getOffset(),
                (int) pageable.getOffset() + maximumPageSize(pageable, productIds)
        );

        final List<Product> recommendedProducts = productRepository.findAllById(pagedProductIds)
                .stream()
                .sorted(new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return productIds.indexOf(p1.getId()) - productIds.indexOf(p2.getId());
                    }
                })
                .collect(Collectors.toList());
        if (productIds.size() < pageable.getPageSize()) {
            int numberRequired = pageable.getPageSize() - productIds.size();
            recommendedProducts.addAll(
                    findMoreProductsByRatingAvg(productIds, numberRequired)
            );
        }
        return new PageImpl<>(
                recommendedProducts,
                pageable,
                Math.max(productIds.size(), DEFAULT_PAGE_SIZE)
        );
    }

    private int maximumPageSize(Pageable pageable, List<Long> productIds) {
        return Math.min(pageable.getPageSize(), (int) (productIds.size() - pageable.getOffset()));
    }

    private RecommendProductDto convertResponseBody(ResponseEntity<String> response) {
        try {
            return objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<RecommendProductDto>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new InvalidRecommendResponseException();
        }
    }

    private Member validateMember(Optional<Long> memberId) {
        memberId.orElseThrow(NotFoundMemberException::new);
        return memberRepository.findById(memberId.get())
                .orElseThrow(NotFoundMemberException::new);
    }
}
