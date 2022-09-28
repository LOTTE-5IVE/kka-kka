package kkakka.mainservice.order.domain;

import kkakka.mainservice.product.domain.Product;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "product_order")
@Getter
public class ProductOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //쿠폰 추가

    private Integer price; //주문가격
    private Integer count; //주문수량

    //==생성 메서드==//
    //주문 상품, 가격, 수량 정보를 사용해서 주문상품 엔티티 생성
    public static ProductOrder createProductOrder(Product product, int price, int count) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setPrice(price);
        productOrder.setCount(count);

        //TODO: 2022.09.28 상품 수량 줄이는 로직 추가해야함 -hyeyeon

        return productOrder;
    }

    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getPrice() * getCount();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
