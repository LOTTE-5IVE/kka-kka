package kkakka.mainservice.category.domain.repository;

import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("SELECT p from Product p join fetch p.category where p.category.categoryId = :categoryId")
//    List<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query(value = "SELECT p from Product p join fetch p.category where p.category.categoryId = :categoryId"
        ,countQuery = "SELECT count(p) FROM Product p where p.category.categoryId = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

}
