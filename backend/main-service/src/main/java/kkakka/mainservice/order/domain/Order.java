package kkakka.mainservice.order.domain;

import kkakka.mainservice.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`ORDER`")
public class Order {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문회원

    //배송정보 추가

    private LocalDateTime orderedAt; //주문시간

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();

    //총 주문금액 추가
    private int totalPrice;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrders.add(productOrder);
        productOrder.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, ProductOrder... productOrders) {
        Order order = new Order();
        order.setMember(member);
        order.setOrderedAt(LocalDateTime.now());

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

    public Long getId() {
        return id;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }
}
