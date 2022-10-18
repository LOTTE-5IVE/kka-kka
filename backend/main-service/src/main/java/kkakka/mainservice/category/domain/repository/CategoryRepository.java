package kkakka.mainservice.category.domain.repository;

import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT p from Product p join fetch p.category where p.category.id = :categoryId"
        , countQuery = "SELECT count(p) FROM Product p where p.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

}
