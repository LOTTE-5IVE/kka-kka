package kkakka.mainservice.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.common.exception.NotFoundCouponException;
import kkakka.mainservice.common.exception.NotFoundMemberException;
import kkakka.mainservice.common.exception.NotFoundProductException;
import kkakka.mainservice.common.exception.NotOrderOwnerException;
import kkakka.mainservice.common.exception.OutOfMinOrderPriceException;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.application.dto.MemberOrderDto;
import kkakka.mainservice.order.application.dto.MemberProductOrderDto;
import kkakka.mainservice.order.application.dto.OrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderDto;
import kkakka.mainservice.order.application.dto.ProductOrderWithCouponDto;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.Recipient;
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
    private final OrderMessageProducer orderMessageProducer;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    final BiFunction<Integer, Integer, Integer> calculatePercentage = new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer productPrice, Integer couponPercentage) {
            return (int) Math.ceil(productPrice * (couponPercentage * 0.01));
        }
    };

    @Transactional
    public Long order(OrderDto orderDto) {
        Long memberId = orderDto.getMemberId();
        List<ProductOrderDto> productOrderDtos = orderDto.getProductOrders();
        Member member = memberRepository.findById(memberId)
            .orElseThrow(NotFoundMemberException::new);
        Recipient recipient = Recipient.from(orderDto.getRecipientDto());

        List<ProductOrder> productOrders = new ArrayList<>();
        int orderTotalPrice = 0;
        for (ProductOrderDto productOrderDto : productOrderDtos) {
            Long productId = productOrderDto.getProductId();
            Long couponId = productOrderDto.getCouponId();
            Integer quantity = productOrderDto.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(NotFoundProductException::new);
            ProductOrder productOrder = ProductOrder.create(
                    product,
                    product.getPrice(),
                    product.getDiscount(),
                    quantity
            );
            if (!Objects.isNull(couponId)) {
                Coupon coupon = couponRepository.findById(couponId).orElseThrow(NotFoundCouponException::new);
                if (product.getDiscountPrice() * quantity < coupon.getMinOrderPrice()) {
                    throw new OutOfMinOrderPriceException();
                }
                productOrder.applyCoupon(coupon);
                MemberCoupon memberCoupon = memberCouponRepository.findAllByCouponIdAndMemberId(couponId, memberId);
                memberCoupon.useCoupon();
            }
            productOrders.add(productOrder);
            orderTotalPrice += productOrder.getTotalPrice();
        }

        Order order = Order.create(member, recipient, orderTotalPrice, productOrders);

        orderRepository.save(order);
        productOrderRepository.saveAll(productOrders);

        sendToKafka(order);
        return order.getId();
    }

    private void sendToKafka(Order order) {
        orderMessageProducer.sendMessage(order);
    }

    public List<MemberOrderDto> showMemberOrders(Long memberId, Long orderId, int pageSize) {
        final List<Order> orders = orderRepositorySupport.findByMemberId(memberId, orderId,
            pageSize);

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

    public int showMemberOrderCount(Long id) {
        final Member member = memberRepository.findById(id)
            .orElseThrow(NotFoundMemberException::new);
        return orderRepository.countAllByMemberId(member.getId());
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

        if (review.isEmpty() && !productOrder.hasCoupon()) {
            return MemberProductOrderDto.create(productOrder, productOrder.getProduct());
        }

        if (review.isEmpty() && productOrder.hasCoupon()) {
            Coupon coupon = productOrder.getCoupon();
            int couponDiscountPrice = calculateCouponDiscountPrice(productOrder, coupon);
            return MemberProductOrderDto.create(productOrder, productOrder.getProduct(),
                    coupon, couponDiscountPrice
            );
        }

        if (review.isPresent() && !productOrder.hasCoupon()) {
            return MemberProductOrderDto.create(productOrder, productOrder.getProduct(),
                    review.get()
            );
        }

        // review.isPresent() && productOrder.hasCoupon()
        Coupon coupon = productOrder.getCoupon();
        int couponDiscountPrice = calculateCouponDiscountPrice(productOrder, coupon);
        return MemberProductOrderDto.create(productOrder, productOrder.getProduct(),
                review.get(),
                coupon, couponDiscountPrice
        );
    }

    private int calculateCouponDiscountPrice(ProductOrder productOrder, Coupon coupon) {
        final int discountedPrice = productOrder.getPrice() - (int) Math.ceil(
                productOrder.getPrice() * (0.01 * productOrder.getDiscount()));

        if (coupon.getPercentage() != null) {
            int calculatedPercentValue = calculatePercentage
                    .apply(discountedPrice, coupon.getPercentage());
            return Math.min(calculatedPercentValue, coupon.getMaxDiscount());
        }
        return coupon.getMaxDiscount();
    }

    @Transactional
    public ProductOrderWithCouponDto applyProductCoupon(Long memberId,
        ProductOrderDto productOrderDto, Long couponId) {
        Product product = productRepository.findById(productOrderDto.getProductId())
            .orElseThrow(NotFoundProductException::new);
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(NotFoundCouponException::new);
        Integer discountedPrice = getDiscountedPrice(coupon, product, productOrderDto.getQuantity());

        MemberCoupon memberCoupon = memberCouponRepository.findAllByCouponIdAndMemberId(couponId,
            memberId);
        memberCoupon.applyCoupon();
        return ProductOrderWithCouponDto.create(productOrderDto.getQuantity(), product,
            discountedPrice, coupon);
    }

    @Transactional
    public void cancelProductCoupon(Long memberId, Long couponId) {
        MemberCoupon memberCoupon = memberCouponRepository.findAllByCouponIdAndMemberId(couponId, memberId);
        memberCoupon.cancelCoupon();
    }

    private Integer getDiscountedPrice(Coupon coupon, Product product, Integer quantity) {
        Integer productDiscount = product.getDiscountPrice() - (product.getDiscountPrice()- product.getMaxDiscount(coupon));
        if (productDiscount * quantity > coupon.getMaxDiscount()) {
            return product.getDiscountPrice() * quantity - coupon.getMaxDiscount();
        }
        return (product.getDiscountPrice() - product.getMaxDiscount(coupon)) * quantity;
    }
}
