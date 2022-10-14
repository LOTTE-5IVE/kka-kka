package kkakka.mainservice.review.domain;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kkakka.mainservice.TestContext;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.common.exception.InvalidArgumentException;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.product.domain.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReviewTest extends TestContext {

    private static Member member;
    private static ProductOrder productOrder;

    @BeforeAll
    static void beforeAll() {
        member = Member.create(
                Provider.create(TEST_MEMBER_01.getProviderId(), MemberProviderName.TEST),
                TEST_MEMBER_01.getName(),
                TEST_MEMBER_01.getEmail(),
                TEST_MEMBER_01.getPhone(),
                TEST_MEMBER_01.getAgeGroup()
        );
        final Product product = new Product(
                (new Category("test-category")),
                "product-name", 1000, 10, "", ""
        );
        productOrder = ProductOrder.create(
                product,
                product.getPrice(),
                1
        );
    }

    @DisplayName("상품후기 제약조건 테스트 - 성공")
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.5, 1.0, 2.5, 4.5, 5.0})
    void createReview_success(Double input) {
        // given
        // when
        // then

        assertThat(Review.create("test-contents", input, member, productOrder))
                .isInstanceOf(Review.class);
    }

    @DisplayName("상품후기 제약조건 테스트 - 실패")
    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -0.5, 0.1, 2.3, 5.1, 5.5, Double.MAX_VALUE})
    void createReview_fail(Double input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> Review.create("test-contents", input, member, productOrder))
                .isInstanceOf(InvalidArgumentException.class);
    }
}
