package kkakka.mainservice.product.ui.dto;

import kkakka.mainservice.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponse {

    private Long id;
    private String name;

    public static ProductCategoryResponse create(Category category) {
        return new ProductCategoryResponse(category.getId(),
                category.getName());
    }
}

