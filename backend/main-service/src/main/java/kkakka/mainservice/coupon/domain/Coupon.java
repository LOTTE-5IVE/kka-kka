package kkakka.mainservice.coupon.domain;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.member.domain.Grade;
import kkakka.mainservice.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Coupon")
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "grade_id")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private String detail;

    @Enumerated(EnumType.STRING)
    private PriceRule priceRule;

    private LocalDateTime registeredAt;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    private Integer percentage;
    private Integer maxDiscount;
    private Integer minOrderPrice;
    private Integer isUsed;

    public Coupon() {

    }


}
