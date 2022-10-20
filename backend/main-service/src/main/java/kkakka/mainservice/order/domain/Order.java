package kkakka.mainservice.order.domain;

import kkakka.mainservice.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`ORDER`")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //TODO: 2022.09.28 배송지정보 추가할 것 -hyeyeon

    private LocalDateTime orderedAt;
    private Integer totalPrice;

    @OneToMany(mappedBy = "order")
    private List<ProductOrder> productOrders;

    public void addProductOrder(ProductOrder productOrder) {
        productOrder.setOrder(this);
    }

    public static Order create(Member member, int totalPrice, List<ProductOrder> productOrders) {
        Order order = new Order(null, member, LocalDateTime.now(), totalPrice, productOrders);
        for (ProductOrder productOrder : productOrders) {
            order.addProductOrder(productOrder);
        }
        return order;
    }

    public boolean isOrderedAtInDay() {
        return this.orderedAt.plusHours(24).isBefore(LocalDateTime.now());
    }
}
