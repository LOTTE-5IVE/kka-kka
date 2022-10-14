package kkakka.mainservice.cart.domain;

import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import kkakka.mainservice.TestContext;
import kkakka.mainservice.cart.domain.repository.CartItemRepository;
import kkakka.mainservice.cart.domain.repository.CartRepository;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CartRepositoryTest extends TestContext {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @DisplayName("장바구니에 처음으로 아이템 추가할 때 잘 되는지 테스트")
    @Test
    void addCartItem(){
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_01.getProviderId(), MemberProviderName.TEST),
                        TEST_MEMBER_01.getName(),
                        TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(),
                        TEST_MEMBER_01.getAgeGroup()
                )
        );
        final Product product = productRepository.save(
                new Product(
                        categoryRepository.save(new Category("test-category")),
                        "product-name", 1000, 10, "", ""
                )
        );
        final Cart cart = cartRepository.save(
                new Cart(member)
        );

        // when
        final CartItem cartItem = CartItem.create(cart, product);
        cartItem.changeQuantity(1);
        cartItemRepository.save(cartItem);
        cart.add(cartItem);

        // then
        final Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow();
        assertThat(savedCart.itemsEmpty()).isFalse();
    }

    @DisplayName("장바구니에 담긴 아이템 변경 테스트")
    @Test
    void changeCartItem(){
        // given
        final Member member = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_01.getProviderId(), MemberProviderName.TEST),
                        TEST_MEMBER_01.getName(),
                        TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(),
                        TEST_MEMBER_01.getAgeGroup()
                )
        );
        final Product product = productRepository.save(
                new Product(
                        categoryRepository.save(new Category("test-category")),
                        "product-name", 1000, 10, "", ""
                )
        );
        final Cart cart = cartRepository.save(
                new Cart(member)
        );

        // when
        final CartItem cartItem = CartItem.create(cart, product);
        cartItem.changeQuantity(1);
        cartItemRepository.save(cartItem);
        cart.add(cartItem);

        // then
        final CartItem savedItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),
                        product.getId())
                .orElseThrow();
        savedItem.changeQuantity(3);

        assertThat(cart.getCartItems().get(0).getQuantity()).isEqualTo(3);
    }
}
