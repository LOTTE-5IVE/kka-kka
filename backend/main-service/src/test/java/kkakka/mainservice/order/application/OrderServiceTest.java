package kkakka.mainservice.order.application;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;
import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_2;
import static kkakka.mainservice.fixture.TestMember.TEST_MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.common.exception.OutOfStockException;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class OrderServiceTest extends TestContext {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(
                Member.create(
                        Provider.create(TEST_MEMBER_01.getCode(), MemberProviderName.TEST),
                        TEST_MEMBER_01.getName(),
                        TEST_MEMBER_01.getEmail(),
                        TEST_MEMBER_01.getPhone(),
                        TEST_MEMBER_01.getAgeGroup()
                )
        );
    }

    @Test
    @DisplayName("상품1개 주문 - 성공")
    public void productOrderOneTest_success() {
        //given
        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), 2);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);

        OrderRequest orderRequest = new OrderRequest(productOrderDtos);

        //when
        Long orderId = orderService.order(OrderDto.create(member.getId(), orderRequest));

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(PRODUCT_1.getPrice() * 2).isEqualTo(getOrder.getTotalPrice());
    }

    @Test
    @DisplayName("상품 2개 이상 주문 - 성공")
    public void productOrderMoreTest_success() {
        //given
        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), 2);
        ProductOrderDto productOrderDto2 = new ProductOrderDto(PRODUCT_2.getId(), 3);

        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);
        productOrderDtos.add(productOrderDto2);

        OrderRequest orderRequest = new OrderRequest(productOrderDtos);

        //when
        Long orderId = orderService.order(OrderDto.create(member.getId(), orderRequest));

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(PRODUCT_1.getPrice() * 2 + PRODUCT_2.getPrice() * 3).isEqualTo(
                getOrder.getTotalPrice());
    }

    @Test
    @DisplayName("상품주문 - 실패(재고수량초과)")
    public void productOrder_fail_inventoryExceeded() {
        //given
        ProductOrderDto productOrderDto1 = new ProductOrderDto(PRODUCT_1.getId(), 11);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        productOrderDtos.add(productOrderDto1);

        OrderRequest orderRequest = new OrderRequest(productOrderDtos);

        //when
        //then
        Assertions.assertThrows(OutOfStockException.class, () -> {
            orderService.order(OrderDto.create(member.getId(), orderRequest));
        });
    }
}
