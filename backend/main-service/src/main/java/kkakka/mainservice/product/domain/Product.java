package kkakka.mainservice.product.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Date registered_at;

    public Product(Long id, Category category, String name, int price, int stock, String imageUrl,
            String detailImageUrl, String nutritionInfoUrl) {
        this(id, category, name, price, stock, imageUrl, detailImageUrl, nutritionInfoUrl, 0,
                new Date());
    }

    public Product(Category category, String name, int price, int stock, String imageUrl,
            String detailImageUrl) {
        this(null, category, name, price, stock, imageUrl, detailImageUrl, "");
    }

    public void reduceStock(int orderQuantity) {
        int restStock = this.stock - orderQuantity;
        this.stock = restStock;
    }

    public boolean isStock(int quantity) {
        return this.stock - quantity > 0;
    }

}
