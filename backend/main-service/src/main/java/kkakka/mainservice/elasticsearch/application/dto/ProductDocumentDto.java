package kkakka.mainservice.elasticsearch.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDocumentDto {

    private Long id;
    @JsonProperty("category")
    private CategoryDocumentDto categoryDocumentDto;
    private String name;
    private Integer price;
    private String imageUrl;
    private int discount;
    private double ratingAvg;

    public static ProductDocumentDto toDto(Product product,
        CategoryDocumentDto categoryDocumentDto) {
        return new ProductDocumentDto(product.getId(),
            categoryDocumentDto,
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getDiscount(),
            product.getRatingAvg()
        );

    }
}
