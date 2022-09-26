package kkakka.mainservice.order.domain;

import kkakka.mainservice.product.domain.Product;

import javax.persistence.*;

@Entity
@Table(name = "product_order")
public class ProductOrder {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    //쿠폰 추가

    private int price; //주문가격
    private int count; //주문수량
}
