package kkakka.mainservice.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMessage {

    private Long id;
    private String name;
    private Integer price;
    @JsonProperty("image_url")
    private String imageUrl;
    private Integer discount;

    public static ProductMessage create(Long id, String name, Integer price, String imageUrl,
            Integer discount) {
        return new ProductMessage(id, name, price, imageUrl, discount);
    }
}