package kkakka.mainservice.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.OrderRepositorySupport;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.order.ui.dto.OrderResponse;
import kkakka.mainservice.order.ui.dto.ProductOrderResponse;
import kkakka.mainservice.order.ui.dto.ProductResponse;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderRepositorySupport orderRepositorySupport;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public Long order(OrderDto orderDto) {

        Long memberId = orderDto.getMemberId();
        List<ProductOrderDto> productOrderDtos = orderDto.getProductOrders();
        Member member = memberRepository.findById(memberId).orElseThrow();

        List<ProductOrder> productOrders = new ArrayList<>();
        int orderTotalPrice = 0;
        for (ProductOrderDto productOrderDto : productOrderDtos) {
            Long productId = productOrderDto.getProductId();
            Integer quantity = productOrderDto.getQuantity();

            Product product = productRepository.findById(productId).get();
            ProductOrder productOrder = ProductOrder.create(product, product.getPrice(), quantity);

            productOrders.add(productOrder);
            orderTotalPrice += productOrder.getTotalPrice();
        }

        Order order = Order.create(member, productOrders, orderTotalPrice);

        orderRepository.save(order);
        productOrderRepository.saveAll(productOrders);

        return order.getId();
    }

    public List<OrderResponse> showMemberOrders(Long memberId, Long orderId, int pageSize) {
        return orderRepositorySupport.findByMemberId(memberId, orderId, pageSize).stream()
                .map(order -> OrderResponse.create(
                        order.getId(),
                        order.getOrderedAt(),
                        order.getTotalPrice(),
                        order.getProductOrders()
                                .stream().map(productOrder -> ProductOrderResponse.create(
                                        productOrder.getId(),
                                        productOrder.getPrice(),
                                        productOrder.getCount(),
                                        ProductResponse.create(
                                                productOrder.getProduct().getId(),
                                                productOrder.getProduct().getName(),
                                                productOrder.getProduct().getPrice(),
                                                productOrder.getProduct().getImageUrl(),
                                                productOrder.getProduct().getDiscount()
                                        ))).collect(Collectors.toList()))
                ).collect(Collectors.toList());
    }
}
