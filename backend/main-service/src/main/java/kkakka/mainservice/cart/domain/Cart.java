package kkakka.mainservice.cart.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import kkakka.mainservice.member.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    public Cart(Member member) {
        this(null, member, new ArrayList<>());
    }

    public void add(CartItem cartItem) {
        cartItem.toCart(this);
        cartItems.add(cartItem);
    }

    public boolean itemsEmpty() {
        return cartItems.isEmpty();
    }

    public void empty() {
        cartItems = new ArrayList<>();
    }

    public void empty(List<CartItem> items) {
        cartItems.removeAll(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) && Objects.equals(member, cart.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, member);
    }
}
