package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.common.dto.ResponsePageDto;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponsePageDto getProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> itemList = categoryRepository.findByCategoryId(categoryId, pageable);
        return ResponsePageDto.from(itemList);
    }
}
