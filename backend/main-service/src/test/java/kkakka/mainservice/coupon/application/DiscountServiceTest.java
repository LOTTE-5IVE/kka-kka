package kkakka.mainservice.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.coupon.domain.DiscountType;
import kkakka.mainservice.coupon.domain.repository.DiscountRepository;
import kkakka.mainservice.coupon.ui.dto.DiscountRequestDto;
import kkakka.mainservice.coupon.ui.dto.DiscountResponseDto;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DiscountServiceTest extends TestContext {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    DiscountService discountService;

    @DisplayName("상품 할인 등록 - 성공")
    @Test
    void createProductDiscount_success() {
        // given
        Product product = new Product(null, null, "상품", 2000,
            10, "url", "detailurl", "nuturl");
        productRepository.save(product);

        // when
        discountService.createDiscount(new DiscountRequestDto(
            null, product.getId(), "test", 10, DiscountType.PRODUCT_DISCOUNT.name(),
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0)));

        // then
        Product savedProduct = productRepository.findById(product.getId()).orElseThrow(
            KkaKkaException::new);
        assertThat(discountRepository.findAll().size()).isEqualTo(1);
        assertThat(savedProduct.getDiscount()).isEqualTo(10);
    }

    @DisplayName("카테고리 할인 등록 - 성공")
    @Test
    void createCategoryDiscount_success() {
        // given
        Category category = new Category("test");
        categoryRepository.save(category);
        Product product = new Product(null, category, "상품", 2000,
            10, "url", "detailurl", "nuturl");
        productRepository.save(product);

        // when
        DiscountRequestDto discountRequestDto = new DiscountRequestDto(
            category.getId(), null, "test", 10, DiscountType.CATEGORY_DISCOUNT.name(),
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0));
        discountService.createDiscount(discountRequestDto);

        // then
        Product savedProduct = productRepository.findById(product.getId())
            .orElseThrow(KkaKkaException::new);
        assertThat(discountRepository.findAll().size()).isEqualTo(1);
        assertThat(savedProduct.getDiscount()).isEqualTo(discountRequestDto.getDiscount());
    }

    @DisplayName("할인 삭제")
    @Test
    public void deleteDiscount_success() {
        // given
        Product product = new Product(null, null, "상품", 2000,
            10, "url", "detailurl", "nuturl");
        productRepository.save(product);
        Long discountId = discountService.createDiscount(new DiscountRequestDto(
            null, product.getId(), "test", 10, DiscountType.PRODUCT_DISCOUNT.name(),
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0)));

        // when
        discountService.deleteDiscount(discountId);

        // then
        Product savedProduct = productRepository.findById(product.getId())
            .orElseThrow(KkaKkaException::new);
        assertThat(savedProduct.getDiscount()).isEqualTo(0);
    }

    @DisplayName("할인 조회")
    @Test
    public void showDiscounts_success() {
        // given
        Product product = new Product(null, null, "상품", 2000,
            10, "url", "detailurl", "nuturl");
        productRepository.save(product);
        Long discountId = discountService.createDiscount(new DiscountRequestDto(
            null, product.getId(), "test", 10, DiscountType.PRODUCT_DISCOUNT.name(), 
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 1, 1, 0, 0)));

        // when
        List<DiscountResponseDto> discounts = discountService.showAllDiscountsNotDeleted();

        // then
        assertThat(discounts.size()).isEqualTo(1);
    }
}
