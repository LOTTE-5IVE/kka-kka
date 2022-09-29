package kkakka.mainservice.order.application;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.order.ui.dto.OrderRequestDto;
import kkakka.mainservice.order.ui.dto.ProductOrderRequest;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ProductRepository productRepository, ProductOrderRepository productOrderRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productOrderRepository = productOrderRepository;
    }

    @Transactional
    public Long order(OrderRequestDto orderRequestDto) {

        Long memberId = orderRequestDto.getMemberId();
        List<ProductOrderRequest> productOrderRequests = orderRequestDto.getProductOrderRequests();
        Member member = memberRepository.findById(memberId).orElseThrow();

        //상품주문 생성
        List<ProductOrder> productOrders = new ArrayList<>();
        int orderTotalPrice = 0;
        for (ProductOrderRequest productOrderRequest : productOrderRequests) {
            Long productId = productOrderRequest.getProductId();
            Integer quantity = productOrderRequest.getQuantity();

            Product product = productRepository.findById(productId).get();
            ProductOrder productOrder = ProductOrder.create(product, product.getPrice(), quantity);

            productOrders.add(productOrder);
            orderTotalPrice += productOrder.getTotalPrice();
        }

        Order order = Order.create(member, productOrders, orderTotalPrice );

        orderRepository.save(order);
        productOrderRepository.saveAll(productOrders);

        return order.getId();
    }

}
