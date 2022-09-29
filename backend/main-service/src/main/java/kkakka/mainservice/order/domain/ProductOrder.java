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

    //TODO: 2022.09.28 쿠폰 추가해야함 -hyeyeon

    private Integer price; //주문가격
    private Integer count; //주문수량

    public static ProductOrder createProductOrder(Product product, int price, int quantity) throws Exception {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProduct(product);
        productOrder.setPrice(price);
        productOrder.setCount(quantity);

        product.reduceStock(quantity);

        return productOrder;
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
