package kkakka.mainservice.order.domain;

import kkakka.mainservice.common.exception.OutOfStockException;
import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product_order")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private Integer quantity; //주문수량
    private Integer deleted;

    public static ProductOrder create(Product product, int price, int quantity) {
        ProductOrder productOrder = new ProductOrder(null, null, product, price, quantity, 0);

        if (product.inStock(quantity)) {
            product.reduceStock(quantity);
            return productOrder;
        }
        throw new OutOfStockException();
    }

    public int getTotalPrice() {
        return getPrice() * getQuantity();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        this.deleted = 1;
    }

    public boolean isOrderedAtInDay() {
        return this.order.isOrderedAtInDay();
    }
}
