package kkakka.mainservice.cart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kkakka.mainservice.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer coupon_id;

    @Column(nullable = false)
    private Integer quantity;
    private Integer price;

    public CartItem(Cart cart, Product product, Integer quantity) {
        this(null, cart, product, null, quantity, null);
    }

    public static CartItem create(Cart cart, Product product, Integer quantity) {
        return new CartItem(cart, product, quantity);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
