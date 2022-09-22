package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategory;
import kkakka.mainservice.product.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ResponseCategory> getProductsByCategoryId(Long categoryId) {

        List<Product> products = categoryRepository.findByCategoryId(categoryId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ResponseCategory> result = new ArrayList<>();
        products.forEach(v -> {
            result.add(mapper.map(v, ResponseCategory.class));
        });

        return result;
    }
}
