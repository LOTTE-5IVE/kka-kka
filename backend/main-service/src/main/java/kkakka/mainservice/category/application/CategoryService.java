package kkakka.mainservice.category.application;

import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.category.ui.dto.ResponseCategory;
import kkakka.mainservice.product.domain.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<ResponseCategory> getProductsByCategoryId(Long categoryId, Pageable pageable) {


        Page<Product> products = categoryRepository.findByCategoryId(categoryId,pageable);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Page<ResponseCategory> responseCategories = products.map(p ->
                        new ResponseCategory(p.getProductId(),
                                p.getName(),
                                p.getPrice(),
                                p.getImage_url(),
                                p.getDiscount(),
                                p.getRegistered_at()
                        ));

//        List<ResponseCategory> result = new ArrayList<>();
//        products.forEach(v -> {
//            result.add(mapper.map(v, ResponseCategory.class));
//        });

        return responseCategories;
    }
}
