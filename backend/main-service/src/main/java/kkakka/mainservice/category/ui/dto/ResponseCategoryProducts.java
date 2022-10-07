package kkakka.mainservice.category.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ResponseCategoryProducts {

    private Long id;
    private String name;
    private Integer price;
    @JsonProperty("image_url")
    private String imageUrl;
    private Integer discount;
    private Date registeredAt;

    public static ResponseCategoryProducts from(Product product) {
        return new ResponseCategoryProducts(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getDiscount(),
                product.getRegistered_at());
    }
}
