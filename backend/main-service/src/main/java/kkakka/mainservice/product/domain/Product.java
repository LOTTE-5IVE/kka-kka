package kkakka.mainservice.product.domain;

import kkakka.mainservice.category.domain.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(nullable = false, length = 255)
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

}
