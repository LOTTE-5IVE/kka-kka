package kkakka.mainservice.product.application.recommend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendProductDto {

    @JsonProperty("product_ids")
    private List<Long> productIds;
}
