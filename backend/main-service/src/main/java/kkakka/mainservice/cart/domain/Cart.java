package kkakka.mainservice.cart.domain;

import kkakka.mainservice.member.domain.Member;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID")
    private Long Id;

    @OneToMany(mappedBy = "cart")
    List<CartItem> cartItems = new ArrayList<>();

//    @OneToOne
//    private Member member;
}
