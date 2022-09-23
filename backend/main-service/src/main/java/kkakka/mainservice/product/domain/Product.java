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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column(nullable = false)
    private String name;
    private int price;
    private int stock;

    @Column(nullable = true)
    private String image_url;
    private String detail_image_url;
    private String nutrition_info_url;
    @ColumnDefault("0")
    private int discount;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date registered_at;

    public Product(Category category, String name, int price, int stock, String image_url, String detail_image_url) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image_url = image_url;
        this.detail_image_url = detail_image_url;
    }
}
