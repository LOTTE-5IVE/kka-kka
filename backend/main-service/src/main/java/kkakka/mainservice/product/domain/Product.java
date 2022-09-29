package kkakka.mainservice.product.domain;

import kkakka.mainservice.category.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date registered_at;

    public Product(Long id, Category category, String name, int price, int stock, String imageUrl, String detailImageUrl, String nutritionInfoUrl) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.detailImageUrl = detailImageUrl;
        this.nutritionInfoUrl = nutritionInfoUrl;
    }

    public Product(Category category, String name, int price, int stock, String imageUrl, String detailImageUrl) {
        this(null, category, name, price, stock, imageUrl, detailImageUrl, null);
    }

    public void reduceStock(int orderQuantity) {
        int restStock = this.stock - orderQuantity;
        this.stock = restStock;
    }

    public boolean isStock(int quantity){
        return this.stock-quantity > 0;
    }

}
