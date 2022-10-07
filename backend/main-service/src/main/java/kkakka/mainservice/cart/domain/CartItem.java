package kkakka.mainservice.cart.domain;

import kkakka.mainservice.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cartitem")
@NoArgsConstructor
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private Integer coupon_id;

    @Column(nullable = false)
    private Integer quantity;

    private CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItem create(Cart cart, Product product, Integer quantity) {
        return new CartItem(cart, product, quantity);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
