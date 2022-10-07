package kkakka.mainservice.order.application;

import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.MemberProviderName;
import kkakka.mainservice.member.member.domain.Provider;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.ui.dto.OrderRequest;
import kkakka.mainservice.order.ui.dto.ProductOrderRequest;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static kkakka.mainservice.fixture.TestMember.MEMBER_01;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceTest {

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
        member = memberRepository.save(Member.create(Provider.create(MEMBER_01.getAccessToken(),
           MemberProviderName.TEST)));
    }

    @Test
    @DisplayName("상품1개 주문 - 성공")
    public void productOrderOneTest_success() throws Exception {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L, 2);
        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);

        OrderRequest orderRequest = new OrderRequest(member.getId(), productOrderRequests);

        //when
        Long orderId = orderService.order(orderRequest);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(2000 * 2).isEqualTo(getOrder.getTotalPrice());
    }

    @Test
    @DisplayName("상품 2개 이상 주문 - 성공")
    public void productOrderMoreTest_success() {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L, 2);
        ProductOrderRequest productOrderRequest2 = new ProductOrderRequest(2L, 3);

        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);
        productOrderRequests.add(productOrderRequest2);

        OrderRequest orderRequest = new OrderRequest(member.getId(), productOrderRequests);

        //when
        Long orderId = orderService.order(orderRequest);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(2000 * 2 + 1000 * 3).isEqualTo(getOrder.getTotalPrice());
    }

    @Test
    @DisplayName("상품주문 - 실패(재고수량초과)")
    public void productOrder_fail_inventoryExceeded() {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L, 11);
        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);

        OrderRequest orderRequest = new OrderRequest(member.getId(), productOrderRequests);

        //when
        //then
        Assertions.assertThrows(IllegalStateException.class, () -> {
           orderService.order(orderRequest);
        });
    }


}
