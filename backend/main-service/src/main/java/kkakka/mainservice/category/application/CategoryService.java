package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategoryProducts;
import kkakka.mainservice.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<ResponseCategoryProducts> getProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> products = categoryRepository.findByCategoryId(categoryId, pageable);
        Page<ResponseCategoryProducts> responseCategoryProducts = products.map(p ->
                new ResponseCategoryProducts(p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getImage_url(),
                        p.getDiscount(),
                        p.getRegistered_at()
                ));
        return responseCategoryProducts;
    }
}
