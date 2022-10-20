package kkakka.mainservice.product.domain;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kkakka.mainservice.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String name;
    private Integer price;
    private Integer stock;

    @Column(nullable = true)
    private String imageUrl;
    private String detailImageUrl;
    private String nutritionInfoUrl;
    @ColumnDefault("0")
    private Integer discount;

    @Column(nullable = false, updatable = false, insertable = false,
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date registeredAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrition_id")
    private Nutrition nutrition;

    private Double ratingAvg;

    public Product(Long id, Category category, String name, int price, int stock, String imageUrl,
        String detailImageUrl, String nutritionInfoUrl, Nutrition nutrition) {
        this(id, category, name, price, stock, imageUrl, detailImageUrl, nutritionInfoUrl, 0,
            new Date(), nutrition, 0.0);
    }

    public Product(Category category, String name, int price, int stock, String imageUrl,
        String detailImageUrl, Nutrition nutrition) {
        this(null, category, name, price, stock, imageUrl, detailImageUrl, "", nutrition);
    }

    public void reduceStock(int orderQuantity) {
        this.stock -= orderQuantity;
    }

    public boolean inStock(int quantity) {
        return this.stock - quantity > 0;
    }

    public void changeDiscount(int discount) {
        this.discount = discount;
    }

    public void deleteDiscount() {
        this.discount = 0;
    }

    public void updateRatingAvg(Double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
