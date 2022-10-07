package kkakka.mainservice.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kkakka.mainservice.member.domain.Member;
import lombok.Getter;

@Entity
@Table(name = "`ORDER`")
@Getter
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

    public Order() {

    }

    public void addProductOrder(ProductOrder productOrder) {
        productOrder.setOrder(this);
    }

    public static Order create(Member member, List<ProductOrder> productOrders, int totalPrice) {

        Order order = new Order(null, member, LocalDateTime.now(), totalPrice);

        for (ProductOrder productOrder : productOrders) {
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
