package kkakka.mainservice.review.domain;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Product;
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

    @DisplayName("상품 id로 후기를 조회할 수 있는지 테스트")
    @Test
    void findByProductId() {
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(MEMBER_01.getProviderId(), MemberProviderName.TEST),
                        MEMBER_01.getName(),
                        MEMBER_01.getEmail(),
                        MEMBER_01.getPhone(),
                        MEMBER_01.getAgeGroup()
                )
        );
        final Product product = productRepository.save(
                new Product(
                        categoryRepository.save(new Category("test-category")),
                        "product-name", 1000, 10, "", ""
                )
        );
        final ProductOrder productOrder = productOrderRepository.save(
                ProductOrder.create(
                        product,
                        product.getPrice(),
                        1
                )
        );
        final Order order = orderRepository.save(
                Order.create(member, List.of(productOrder), product.getPrice()));
        final Review review = reviewRepository.save(
                Review.create("test-review", member, productOrder)
        );

        // when
        final Page<Review> reviews = reviewRepository.findAllByProductId(product.getId(),
                Pageable.unpaged());
        // then
        assertThat(reviews).hasSize(1);
    }

}
