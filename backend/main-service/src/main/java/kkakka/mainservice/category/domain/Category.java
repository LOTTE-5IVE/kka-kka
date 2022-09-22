package kkakka.mainservice.category.domain;

import kkakka.mainservice.product.domain.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<Product>();
}
