package kkakka.mainservice.cart.domain;

import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @BatchSize(size = 10)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private Integer quantity;
    private Integer price;

    public static CartItem create(Cart cart, Product product) {
        return new CartItem(null, cart, product, null, 0, null);
    }

    public void changeQuantity(int quantity) {
        if (quantity < 0) {
            return;
        }
        this.quantity = quantity;
        this.price = product.getPrice() * this.quantity;
    }

    public void toCart(Cart cart) {
        this.cart = cart;
    }

    public void applyCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void cancelCoupon(Coupon coupon) {
        if (coupon == this.coupon) {
            this.coupon = null;
        }
    }

    public Integer getDiscountedPrice(Coupon coupon) {
        Integer productDiscount = product.getDiscountPrice() - (product.getDiscountPrice()- product.getMaxDiscount(coupon));
        if (productDiscount * this.quantity > coupon.getMaxDiscount()) {
            return product.getDiscountPrice() * this.quantity - coupon.getMaxDiscount();
        }
        return (product.getDiscountPrice() - product.getMaxDiscount(coupon)) * this.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
