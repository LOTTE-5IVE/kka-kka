package kkakka.mainservice.coupon.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Discount")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private Integer discount;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    public static Discount create(Category category, String name, Integer discount,
        LocalDateTime startedAt, LocalDateTime expiredAt) {
        return new Discount(null,
            category, null, name, discount, startedAt, expiredAt, false,
            DiscountType.CATEGORY_DISCOUNT);
    }

    public static Discount create(Product product, String name, Integer discount,
        LocalDateTime startedAt, LocalDateTime expiredAt) {
        return new Discount(null,
            null, product, name, discount, startedAt, expiredAt, false,
            DiscountType.PRODUCT_DISCOUNT);
    }

    public void deleteDiscount() {
        this.isDeleted = true;
    }

    public Long getCategoryId() {
        if (category != null) {
            return category.getId();
        }
        return null;
    }

    public Long getProductId() {
        if (product != null) {
            return product.getId();
        }
        return null;
    }
}
