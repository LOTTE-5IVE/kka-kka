package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponsePageDto getProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> itemList = categoryRepository.findByCategoryId(categoryId, pageable);
        return ResponsePageDto.from(
                itemList,
                itemList.getContent().stream()
                        .map(ResponseCategoryProducts::from)
                        .collect(Collectors.toList())
        );
    }
}
