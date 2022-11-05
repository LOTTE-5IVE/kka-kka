package kkakka.mainservice.elasticsearch.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.elasticsearch.application.dto.SearchParamDto;
import kkakka.mainservice.elasticsearch.ui.dto.SearchResultResponse;
import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.NutritionRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class ProductDocumentServiceTest extends TestContext {

    @Autowired
    ProductDocumentService productDocumentService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    NutritionRepository nutritionRepository;

    private Product product;
    private Category category;
    private Nutrition nutrition;

    @BeforeEach
    void setUp() {
        nutrition = nutritionRepository.save(
            new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370", "0"));
        category = categoryRepository.save(new Category("categoryTest"));
        product = productRepository.save(
            new Product(null,category,"롯데 테스트 과자 100g", 1200, 10, "testImgUrl","detailUrl","nutritionTestUrl",0, new Date(),nutrition,0.0)
        );
    }

    @DisplayName("ES 검색 조회 - 성공")
    @Test
    public void showProductBySearchTest() {
        //given
        final String keyword = "테스트";
        SearchParamDto searchParamDto = SearchParamDto.create(keyword, "accuracy", null,null,null,null,null);

        //when
        SearchResultResponse response = productDocumentService.findByKeyword(
            searchParamDto,
            Pageable.ofSize(9)
        );

        //then
        assertThat(response.getTotalHits()).isEqualTo(1);
    }

}
