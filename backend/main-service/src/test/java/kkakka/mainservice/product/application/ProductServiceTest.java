package kkakka.mainservice.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.product.application.dto.ProductDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ProductServiceTest extends TestContext {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("검색 조회 - 성공")
    @Test
    public void showProductsBySearchTest() {
        //given
        Category category = categoryRepository.save(new Category("초콜릿"));
        Product product = productRepository.save(
                new Product(null, category, "단백질 초코바 120g", 1200, 10, "imageUrl", "detailImageUrl",
                        "nutritionInfoUrl", 0, new Date())
        );

        //when
        final String keyword = "단백질";
        Page<ProductDto> productDtos = productService.showProductsBySearch(keyword,
                Pageable.ofSize(9));

        //then
        assertThat(productDtos.getContent().get(0).getName()).isEqualTo(product.getName());
    }
}
