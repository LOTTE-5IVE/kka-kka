package kkakka.mainservice.category.domain;

import kkakka.mainservice.product.domain.Product;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<Product>();

    public List<Product> getProductList() {
        return productList;
    }

    public Category(String name) {
        this.name = name;
    }
}
