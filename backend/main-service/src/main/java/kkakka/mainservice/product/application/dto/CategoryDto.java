package kkakka.mainservice.product.application.dto;


import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.ui.dto.ProductCategoryResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDto {

    private Long id;
    private String name;

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public ProductCategoryResponse toResponseDto() {
        return new ProductCategoryResponse(this.id, this.name);
    }
}