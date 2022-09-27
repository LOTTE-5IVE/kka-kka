package kkakka.mainservice.order.application;

import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long productId, int count) {

        //엔티티 조회
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Product> product = productRepository.findById(memberId);

        //주문상품 생성
        ProductOrder productOrder = ProductOrder.createProductOrder(product.get(),product.get().getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member.get(), productOrder);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }
}
