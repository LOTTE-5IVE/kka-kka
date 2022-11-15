package kkakka.mainservice.elasticsearch.application.dto;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.application.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDocumentDto {

    private Long id;
    private String name;

    public static CategoryDocumentDto toDto(Category category) {
        return new CategoryDocumentDto(category.getId(), category.getName());
    }
}
