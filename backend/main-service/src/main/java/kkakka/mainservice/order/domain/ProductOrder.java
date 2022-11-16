package kkakka.mainservice.order.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kkakka.mainservice.common.exception.IllegalQuantityException;
import kkakka.mainservice.common.exception.OutOfStockException;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @BatchSize(size = 100)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private Integer price;
    private Integer discount;
    private Integer quantity;
    private Integer deleted;

    public static ProductOrder create(Product product, int price, int discount, int quantity) {
        ProductOrder productOrder = new ProductOrder(
                null,
                null,
                product,
                null,
                price,
                discount,
                quantity,
                0
        );

        if (quantity < 1) {
            throw new IllegalQuantityException();
        }

        if (product.inStock(quantity)) {
            product.reduceStock(quantity);
            return productOrder;
        }
        throw new OutOfStockException();
    }

    public void applyCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public int getTotalPrice() {
        return (int) ((getPrice() - Math.ceil(getPrice() * getDiscount() * 0.01)) * getQuantity());
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
