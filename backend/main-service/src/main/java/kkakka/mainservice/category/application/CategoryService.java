package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
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

    public Page<ResponseCategoryProducts> getProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> products = categoryRepository.findByCategoryId(categoryId, pageable);
        Page<ResponseCategoryProducts> responseCategoryProducts = products.map(p ->
                ResponseCategoryProducts.from(p));
        return responseCategoryProducts;
    }
}
