package kkakka.mainservice.product.application.recommend.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import kkakka.mainservice.common.exception.InvalidRecommendResponseException;
import kkakka.mainservice.product.application.recommend.RecommendProductIds;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.infrastructure.redis.RecommendRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class ProductRecommendStrategy implements ProductRecommender {

    @Value("${recommend.request-url}")
    private String RECOMMENDATION_SERVER_URL;

    private static final int DEFAULT_PAGE_SIZE = 9;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final RecommendRedisRepository recommendRedisRepository;

    @Override
    public Page<Product> recommend(Optional<Long> recommendPivotId, Pageable pageable) {
        if (recommendPivotId.isEmpty()) {
            return Page.empty();
        }

        final Optional<Product> product = productRepository.findById(recommendPivotId.get());
        if (product.isEmpty()) {
            return Page.empty();
        }

        final Product pivotProduct = product.get();
        final RecommendProductIds recommendProductIds = requestRecommendation(pivotProduct);

        return findRecommendedProducts(pageable, recommendProductIds);
    }

    private RecommendProductIds requestRecommendation(Product pivotProduct) {
        Optional<RecommendProductIds> recommendProductIdsSavedInRedis = findFromRedis(pivotProduct);
        if (recommendProductIdsSavedInRedis.isPresent()) {
            return recommendProductIdsSavedInRedis.get();
        }

        final ResponseEntity<String> response = restTemplate.exchange(
                RECOMMENDATION_SERVER_URL + pivotProduct.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        final RecommendProductIds recommendProductIds = convertResponseBody(pivotProduct.getId(),
                response);
        saveInRedis(recommendProductIds);
        return recommendProductIds;
    }

    private void saveInRedis(RecommendProductIds recommendProductIds) {
        recommendRedisRepository.save(recommendProductIds);
    }

    private Optional<RecommendProductIds> findFromRedis(Product pivotProduct) {
        final Optional<RecommendProductIds> savedRecommendIds = recommendRedisRepository.findById(
                pivotProduct.getId().toString());
        if (savedRecommendIds.isPresent()) {
            if (Objects.isNull(savedRecommendIds.get().getProductIds())) {
                return Optional.of(new RecommendProductIds(pivotProduct.getId().toString(),
                        Collections.emptyList()));
            }
            return Optional.of(savedRecommendIds.get());
        }
        return Optional.empty();
    }

    private RecommendProductIds convertResponseBody(Long pivotId, ResponseEntity<String> response) {
        try {
            if (Objects.isNull(response.getBody())) {
                return new RecommendProductIds(pivotId.toString(), Collections.emptyList());
            }
            return objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<RecommendProductIds>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new InvalidRecommendResponseException();
        }
    }

    private PageImpl<Product> findRecommendedProducts(
            Pageable pageable,
            RecommendProductIds recommendProductIds
    ) {
        final List<Long> productIds = recommendProductIds.getProductIds();
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

    private List<Product> findMoreProductsByRatingAvg(List<Long> productIds, int numberRequired) {
        return productRepository.findAllOrderByRatingAvg(
                productIds,
                Pageable.ofSize(numberRequired)
        ).getContent();
    }

}

