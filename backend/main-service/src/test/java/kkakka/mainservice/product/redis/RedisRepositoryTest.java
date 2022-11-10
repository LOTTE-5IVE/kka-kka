package kkakka.mainservice.product.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.product.application.recommend.RecommendProductIds;
import kkakka.mainservice.product.infrastructure.redis.EmbeddedRedisConfig;
import kkakka.mainservice.product.infrastructure.redis.RecommendRedisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@Import(EmbeddedRedisConfig.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@DataRedisTest
public class RedisRepositoryTest extends TestContext {

    @Autowired
    private RecommendRedisRepository recommendRedisRepository;

    @DisplayName("레디스 기본 저장과 조회 테스트")
    @Test
    void saveAndFind() {
        // given
        final RecommendProductIds recommendProductIds = new RecommendProductIds("1",
                List.of(1L, 2L, 3L, 4L));

        // when
        final RecommendProductIds savedIds = recommendRedisRepository.save(recommendProductIds);

        // then
        final RecommendProductIds findIds = recommendRedisRepository.findById(
                savedIds.getPivotId()).orElse(null);

        assertThat(findIds).isNotNull();
        assertThat(findIds.getProductIds()).isEqualTo(savedIds.getProductIds());
    }
}
