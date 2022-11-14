package kkakka.mainservice.product.infrastructure.redis;

import kkakka.mainservice.product.application.recommend.RecommendProductIds;
import org.springframework.data.repository.CrudRepository;

public interface RecommendRedisRepository extends CrudRepository<RecommendProductIds, String> {

}
