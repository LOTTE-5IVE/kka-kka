package kkakka.mainservice.cart.domain;

import kkakka.mainservice.member.domain.Member;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @OneToOne
//    private Member member;
}
