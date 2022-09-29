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

    public void setMember(Member member) {
        this.member = member;
    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrder.setOrder(this);
    }

    public static Order createOrder(Member member, int totalPrice, List<ProductOrder> productOrders) {
        Order order = new Order();
        order.setMember(member);
        order.setTotalPrice(totalPrice);

        for(ProductOrder productOrder : productOrders) {
            order.addProductOrder(productOrder);
        }

        order.setOrderedAt(LocalDateTime.now());

        return order;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
