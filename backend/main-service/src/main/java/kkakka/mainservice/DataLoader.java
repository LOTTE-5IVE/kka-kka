package kkakka.mainservice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.NutritionRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final NutritionRepository nutritionRepository;

    public static final Map<String, Category> categories = new LinkedHashMap<>();
    private boolean categoryFlag = false;

    @Transactional
    public void saveData(List<String[]> data) {
        saveCategory();
        if (categoryFlag) {
            return;
        }

        for (String[] datum : data) {
            saveProduct(datum);
        }
    }

    public Product saveProduct(String[] productRow) {
        final String category = productRow[0];
        final String name = productRow[1];
        final String price = productRow[2];
        final String defaultImageUrl = productRow[3];
        final String detailImageUrl = productRow[4];
        final String calorie = productRow[5];
        final String carbohydrate = productRow[6];
        final String sugar = productRow[7];
        final String protein = productRow[8];
        final String fat = productRow[9];
        final String saturatedFat = productRow[10];
        final String transFat = productRow[11];
        final String cholesterol = productRow[12];
        final String sodium = productRow[13];
        final String calcium = productRow[14];

        final Nutrition nutrition = nutritionRepository.save(
                new Nutrition(
                        calorie, carbohydrate, sugar, protein,
                        fat, saturatedFat, transFat, cholesterol, sodium, calcium
                )
        );

        return productRepository.save(
                new Product(
                        categories.get(category),
                        name,
                        Integer.parseInt(price),
                        1000,
                        defaultImageUrl,
                        detailImageUrl,
                        nutrition
                )
        );
    }

    public void saveCategory() {
        final List<Category> savedCategories = categoryRepository.findAll();
        savedCategories.forEach((category -> categories.put(category.getName(), category)));

        if (categories.size() > 0) {
            categoryFlag = true;
            return;
        }

        Category category1 = categoryRepository.save(new Category("비스킷/샌드"));
        Category category2 = categoryRepository.save(new Category("스낵/봉지과자"));
        Category category3 = categoryRepository.save(new Category("박스과자"));
        Category category4 = categoryRepository.save(new Category("캔디/사탕/젤리"));
        Category category5 = categoryRepository.save(new Category("시리얼/바"));
        Category category6 = categoryRepository.save(new Category("초콜릿"));
        Category category7 = categoryRepository.save(new Category("껌/자일리톨"));
        Category category8 = categoryRepository.save(new Category("선물세트"));

        categories.put(category1.getName(), category1);
        categories.put(category2.getName(), category2);
        categories.put(category3.getName(), category3);
        categories.put(category4.getName(), category4);
        categories.put(category5.getName(), category5);
        categories.put(category6.getName(), category6);
        categories.put(category7.getName(), category7);
        categories.put(category8.getName(), category8);
    }
}
