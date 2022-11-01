package kkakka.mainservice.review.domain;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.DoubleStream;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.ProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.RecipientDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.Recipient;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Nutrition;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.NutritionRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class ReviewRepositoryTest extends TestContext {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NutritionRepository nutritionRepository;

    @DisplayName("상품 id로 후기를 조회할 수 있는지 테스트")
    @Test
    void findByProductId() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_01.getProviderId(), ProviderName.TEST),
                        TEST_MEMBER_01.getName(),
                        TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(),
                        TEST_MEMBER_01.getAgeGroup()
                )
        );
        final Product product = productRepository.save(
                new Product(
                        categoryRepository.save(new Category("test-category")),
                        "product-name", 1000, 10, "", "",
                        nutritionRepository.save(
                                new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                        "0")
                        )
                )
        );
        final ProductOrder productOrder = productOrderRepository.save(
                ProductOrder.create(
                        product,
                        product.getPrice(),
                        1
                )
        );
        orderRepository.save(
                Order.create(member,
                        Recipient.from(RecipientDto.create(member.getName(), member.getEmail(),
                                member.getPhone(), member.getAddress())), product.getPrice(),
                        List.of(productOrder)));

        final Review review = reviewRepository.save(
                Review.create("test-review", 5.0, member, productOrder)
        );

        // when
        final Page<Review> reviews = reviewRepository.findAllByProductId(product.getId(),
                Pageable.unpaged());

        // then
        assertThat(reviews).hasSize(1);
        assertThat(reviews).contains(review);
    }

    @DisplayName("상품 후기 평점 평균 구하는 테스트")
    @Test
    void productReviewRatingAvgTest() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_01.getProviderId(), ProviderName.TEST),
                        TEST_MEMBER_01.getName(),
                        TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(),
                        TEST_MEMBER_01.getAgeGroup()
                )
        );
        final Product product = productRepository.save(
                new Product(
                        categoryRepository.save(new Category("test-category")),
                        "product-name", 1000, 10, "", "",
                        nutritionRepository.save(
                                new Nutrition("398", "51", "0", "7", "22", "12", "0.5", "35", "370",
                                        "0")
                        )
                )
        );
        final ProductOrder productOrder = productOrderRepository.save(
                ProductOrder.create(
                        product,
                        product.getPrice(),
                        1
                )
        );
        orderRepository.save(
                Order.create(member,
                        Recipient.from(RecipientDto.create(member.getName(), member.getEmail(),
                                member.getPhone(), member.getAddress())), product.getPrice(),
                        List.of(productOrder)));

        final Review review_1 = reviewRepository.save(
                Review.create("test-review", 5.0, member, productOrder)
        );
        final Review review_2 = reviewRepository.save(
                Review.create("test-review", 1.5, member, productOrder)
        );
        final Review review_3 = reviewRepository.save(
                Review.create("test-review", 4.0, member, productOrder)
        );

        // when
        final Double ratingAvg = reviewRepository.findRatingAvgByProductId(product.getId()).get();

        // then
        assertThat(ratingAvg).isEqualTo(
                DoubleStream.of(review_1.getRating(), review_2.getRating(), review_3.getRating())
                        .average().getAsDouble()
        );
    }
}
