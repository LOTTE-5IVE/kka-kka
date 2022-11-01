package kkakka.mainservice.order.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.common.exception.NotOrderOwnerException;
import kkakka.mainservice.member.auth.ui.LoginMember;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.MemberOrderDto;
import kkakka.mainservice.order.application.dto.MemberProductOrderDto;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.OrderRepositorySupport;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import kkakka.mainservice.review.domain.Review;
import kkakka.mainservice.review.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
    private final ReviewRepository reviewRepository;

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

            Product product = productRepository.findById(productId)
                    .orElseThrow(KkaKkaException::new);
            ProductOrder productOrder = ProductOrder.create(product, product.getPrice(), quantity);

            productOrders.add(productOrder);
            orderTotalPrice += productOrder.getTotalPrice();
        }

        Order order = Order.create(member, orderTotalPrice, productOrders);

        orderRepository.save(order);
        productOrderRepository.saveAll(productOrders);

        return order.getId();
    }

    public List<MemberOrderDto> showMemberOrders(Long memberId, Long orderId, int pageSize) {
        final List<Order> orders = orderRepositorySupport.findByMemberId(memberId, orderId,
                pageSize);

        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        List<MemberOrderDto> dtos = new ArrayList<>();
        for (Order order : orders) {
            List<ProductOrder> productOrders = order.getProductOrders();
            List<MemberProductOrderDto> memberProductOrderDtos = toMemberProductOrderListWithProductOrders(
                    memberId, productOrders);
            dtos.add(
                    MemberOrderDto.toDto(
                            order, memberProductOrderDtos
                    )
            );
        }
        return dtos;
    }

    public boolean checkIsLastOrder(Long memberId, Long orderId) {
        final List<Order> orders = orderRepositorySupport.isLastId(memberId, orderId);
        return orders.isEmpty();
    }

    @Transactional
    public void cancelOrder(List<Long> productOrderIds, LoginMember loginMember) {

        Long loginMemberId = loginMember.getId();

        productOrderIds.forEach(productOrderId -> {
            ProductOrder productOrder = productOrderRepository
                    .findByIdAndMemberId(productOrderId, loginMemberId)
                    .orElseThrow(NotOrderOwnerException::new);
            if (!productOrder.isOrderedAtInDay()) {
                productOrder.cancel();
                productOrderRepository.save(productOrder);
            }
        });
    }

    @NotNull
    private List<MemberProductOrderDto> toMemberProductOrderListWithProductOrders(
            Long memberId,
            List<ProductOrder> productOrders
    ) {
        List<MemberProductOrderDto> memberProductOrderDtos = new ArrayList<>();
        for (ProductOrder productOrder : productOrders) {
            final MemberProductOrderDto memberProductOrderDto = toMemberProductOrderDto(
                    memberId, productOrder);
            memberProductOrderDtos.add(memberProductOrderDto);
        }
        return memberProductOrderDtos;
    }

    @NotNull
    private MemberProductOrderDto toMemberProductOrderDto(Long memberId,
                                                          ProductOrder productOrder) {
        final Optional<Review> review = reviewRepository.findByMemberIdAndProductOrderId(
                memberId, productOrder.getId());
        return MemberProductOrderDto.create(productOrder, productOrder.getProduct(), review);
    }
}
