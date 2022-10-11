package kkakka.mainservice.order.domain;

import kkakka.mainservice.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "product_order")
@Getter
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //TODO: 2022.09.28 쿠폰 추가해야함 -hyeyeon

    private Integer price; //주문가격
    private Integer count; //주문수량

    public ProductOrder() {

    }

    public static ProductOrder create(Product product, int price, int quantity) {
        ProductOrder productOrder = new ProductOrder(null, product, price, quantity);

        //재고 확인 로직
        if(!product.isStock(quantity)){
            throw new IllegalStateException("재고수량이 부족합니다.");
        }
        product.reduceStock(quantity);

        return productOrder;
    }

    public int getTotalPrice() {
        return getPrice() * getCount();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductOrder(Long id, Product product, Integer price, Integer count) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.count = count;
    }
}
