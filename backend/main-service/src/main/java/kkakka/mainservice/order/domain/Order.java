package kkakka.mainservice.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kkakka.mainservice.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @Embedded
    private Recipient recipient;

    private LocalDateTime orderedAt;
    private Integer totalPrice;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "order")
    private List<ProductOrder> productOrders;

    public void addProductOrder(ProductOrder productOrder) {
        productOrder.setOrder(this);
    }

    public static Order create(
            Member member,
            Recipient recipient,
            int totalPrice,
            List<ProductOrder> productOrders
    ) {
        Order order = new Order(
                null,
                member,
                recipient,
                LocalDateTime.now(),
                totalPrice,
                productOrders
        );
        for (ProductOrder productOrder : productOrders) {
            order.addProductOrder(productOrder);
        }
        return order;
    }

    public boolean isOrderedAtInDay() {
        return this.orderedAt.plusHours(24).isBefore(LocalDateTime.now());
    }
}
