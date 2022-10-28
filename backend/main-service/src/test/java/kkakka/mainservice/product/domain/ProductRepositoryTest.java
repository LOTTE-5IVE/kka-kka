package kkakka.mainservice.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.NutritionRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NutritionRepository nutritionRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 데이터를 id 목록의 in 절로 잘 찾아오는지 테스트")
    @Test
    void findAllByIdInTest(){
        // given
        final Product product1 = productRepository.save(new Product(
                categoryRepository.save(new Category("test-category")),
                "product-name1", 1000, 10, "", "",
                nutritionRepository.save(
                        new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                "0")
                )
        ));
        final Product product2 = productRepository.save(new Product(
                categoryRepository.save(new Category("test-category")),
                "product-name2", 1000, 10, "", "",
                nutritionRepository.save(
                        new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                "0")
                )
        ));
        final Product product3 = productRepository.save(new Product(
                categoryRepository.save(new Category("test-category")),
                "product-name3", 1000, 10, "", "",
                nutritionRepository.save(
                        new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                "0")
                )
        ));
        final Product product4 = productRepository.save(new Product(
                categoryRepository.save(new Category("test-category")),
                "product-name4", 1000, 10, "", "",
                nutritionRepository.save(
                        new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                "0")
                )
        ));
        final Product product5 = productRepository.save(new Product(
                categoryRepository.save(new Category("test-category")),
                "product-name5", 1000, 10, "", "",
                nutritionRepository.save(
                        new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                "0")
                )
        ));

        // when
        final List<Product> products = productRepository.findAllById(
                List.of(product1.getId(), product2.getId()));

        // then
        assertThat(products).hasSize(2);
        assertThat(products).containsExactly(product1, product2);
    }
}
