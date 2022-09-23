package kkakka.mainservice.cart.domain;

import kkakka.mainservice.product.domain.Product;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cartitem")
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    @OneToOne
    @JoinColumn(name = "id")
    private Product product;

    private Integer coupon_id;

    @Column(nullable = false)
    private Integer quantity;
    private Integer price;

    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

}
