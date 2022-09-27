package kkakka.mainservice.order.application;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Test
    @DisplayName("상품1개 주문 - 성공")
    public void productOrderOneTest_success() {

        //given
        Member member = memberRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();
        int orderCount = 2; //주문수량

        //when
        Long orderId = orderService.order(member.getId(), Collections.singletonList(product.getId()), orderCount);

        //then
        Order getOrder = orderRepository.findById(orderId).get();

        assertThat(getOrder.getProductOrders().size()).isEqualTo(1);
        assertThat(product.getPrice() * orderCount).isEqualTo(getOrder.getTotalPrice());

    }

    @Test
    @DisplayName("상품 2개 이상 주문 - 성공")
    public void productOrderMoreTest_success() {

        //given
        Member member = memberRepository.findById(1L).get();
        List<Long> productIds = new ArrayList<>();
        productIds.add(1L);
        productIds.add(2L);
        int orderCountOne = 2;
        int orderCountTwo = 1;

        //when
        Long orderId = orderService.order(member.getId(), productIds, orderCountOne);

        //then
        Order getOrder = orderRepository.findById(orderId).get();

        assertThat(getOrder.getProductOrders().size()).isEqualTo(2);
    }
}