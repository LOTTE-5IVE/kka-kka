package kkakka.mainservice.cart.domain;

import kkakka.mainservice.product.domain.Product;

import javax.persistence.*;

@Entity
@Table(name = "cartitem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
    @OneToOne
    @JoinColumn(name = "id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
    private Integer price;

}
