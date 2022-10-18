package kkakka.mainservice.review.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kkakka.mainservice.common.exception.InvalidArgumentException;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.order.domain.ProductOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;
    private Double rating;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id")
    private ProductOrder productOrder;

    public static Review create(String contents, Double rating, Member member,
            ProductOrder productOrder) {
        validateContents(contents);
        validateRating(rating);
        return new Review(null, contents, rating, LocalDateTime.now(), member, productOrder);
    }

    public String getMemberName() {
        return this.member.getName();
    }

    private static void validateContents(String contents) {
        if (contents.length() > 200) {
            throw new InvalidArgumentException();
        }
    }

    private static void validateRating(Double rating) {
        if (rating < 0.5 || rating > 5.0 || (rating % 0.5 != 0.5 && rating % 0.5 != 0.0)) {
            throw new InvalidArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
