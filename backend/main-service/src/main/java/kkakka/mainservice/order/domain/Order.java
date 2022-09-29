package kkakka.mainservice.order.domain;

import kkakka.mainservice.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`ORDER`")
@Getter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //TODO: 2022.09.28 배송지정보 추가할 것 -hyeyeon

    private LocalDateTime orderedAt;
    private Integer totalPrice;

    public Order() {

    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrder.setOrder(this);
    }

    public static Order create(Member member, List<ProductOrder> productOrders, int totalPrice) {

        Order order = new Order(null, member, LocalDateTime.now(),totalPrice);

        for(ProductOrder productOrder : productOrders) {
            order.addProductOrder(productOrder);
        }

        return order;
    }

    public Order(Long id, Member member, LocalDateTime orderedAt, Integer totalPrice) {
        this.id = id;
        this.member = member;
        this.orderedAt = orderedAt;
        this.totalPrice = totalPrice;
    }
}
