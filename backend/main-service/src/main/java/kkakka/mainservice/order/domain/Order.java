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
    private Member member; //주문회원

    //TODO: 2022.09.28 배송지정보 추가할 것 -hyeyeon

    private LocalDateTime orderedAt; //주문시간

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();
    private Integer totalPrice; //총금액

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this); //회원에게 주문 추가
    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrders.add(productOrder);
        productOrder.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, int totalPrice, List<ProductOrder> productOrders) {
        Order order = new Order();
        order.setMember(member);
        order.setTotalPrice(totalPrice);

        //상품주문 추가
        for(ProductOrder productOrder : productOrders) {
            order.addProductOrder(productOrder);
        }

        order.setOrderedAt(LocalDateTime.now());

        return order;
    }

    //==조회로직==//
    /** 전체 주문 가격 조회 */
    public int getTotalPrice() {
        totalPrice = 0;

        for(ProductOrder productOrder : productOrders) {
            totalPrice += productOrder.getTotalPrice();
        }

        return totalPrice;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
