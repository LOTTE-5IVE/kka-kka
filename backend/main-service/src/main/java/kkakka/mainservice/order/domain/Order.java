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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders = new ArrayList<>();

    //배송정보

    private LocalDateTime orderedAt; //주문시간

}
