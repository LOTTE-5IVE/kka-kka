package kkakka.mainservice.product.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.product.ui.dto.ProductResponseDto;
import kkakka.mainservice.product.ui.dto.SearchRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @DisplayName("검색 조회 - 성공")
    @Test
    public void showProductsBySearchTest() throws Exception {
        //given
        Category category = categoryRepository.save(new Category("초콜릿"));
        Product product = productRepository.save(
            new Product(null, category, "단백질 초코바 120g", 1200, 10, "imageUrl", "detailImageUrl",
                "nutritionInfoUrl", 0, new Date())
        );
        SearchRequest searchRequest = new SearchRequest("단백질");

        //when
        List<ProductResponseDto> productResponseDtos = productService.showProductsBySearch(
            searchRequest.toDto());

        //then
        assertThat(productResponseDtos.get(0).getName()).isEqualTo(product.getName());
    }
}
