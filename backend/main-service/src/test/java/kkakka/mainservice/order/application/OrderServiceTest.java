package kkakka.mainservice.order.application;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.ui.dto.OrderRequestDto;
import kkakka.mainservice.order.ui.dto.ProductOrderRequest;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    private Product product;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("username1", "user1@email.com"));
    }

    @Test
    @DisplayName("상품1개 주문 - 성공")
    public void productOrderOneTest_success() throws Exception {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L,2);
        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);

        OrderRequestDto orderRequestDto = new OrderRequestDto(member.getId(), productOrderRequests);

        //when
        Long orderId = orderService.order(orderRequestDto);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(2000*2).isEqualTo(getOrder.getTotalPrice());
        assertThat(1).isEqualTo(getOrder.getProductOrders().size());
    }

    @Test
    @DisplayName("상품 2개 이상 주문 - 성공")
    public void productOrderMoreTest_success() throws Exception {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L,2);
        ProductOrderRequest productOrderRequest2 = new ProductOrderRequest(2L,3);

        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);
        productOrderRequests.add(productOrderRequest2);

        OrderRequestDto orderRequestDto = new OrderRequestDto(member.getId(), productOrderRequests);

        //when
        Long orderId = orderService.order(orderRequestDto);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(orderId).isEqualTo(getOrder.getId());
        assertThat(2000*2+1000*3).isEqualTo(getOrder.getTotalPrice());
        assertThat(2).isEqualTo(getOrder.getProductOrders().size());
    }

    @Test
    @DisplayName("상품주문 - 실패(재고수량초과)")
    public void productOrder_fail_inventoryExceeded() throws Exception {

        //given
        ProductOrderRequest productOrderRequest1 = new ProductOrderRequest(1L,11);
        List<ProductOrderRequest> productOrderRequests = new ArrayList<>();
        productOrderRequests.add(productOrderRequest1);

        OrderRequestDto orderRequestDto = new OrderRequestDto(member.getId(), productOrderRequests);

        //when
        orderService.order(orderRequestDto);

        //then
        fail("재고 수량 부족으로 예외가 발생해야 한다.");
    }
}