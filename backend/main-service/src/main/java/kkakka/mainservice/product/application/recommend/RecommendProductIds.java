package kkakka.mainservice.product.application.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("recommend")
public class RecommendProductIds {

    @Id
    @JsonProperty("id")
    private String pivotId;
    @JsonProperty("product_ids")
    private List<Long> productIds;
}
