package kkakka.mainservice.product.domain.repository;

import java.util.List;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Long countBy();

    Page<Product> findAll(Pageable pageable);

    @Query(value = "select p from Product p where p.category.id = :categoryId",
            countQuery = "select count(p) from Product p where p.category.id = :categoryId")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "select p from Product p order by p.ratingAvg desc",
            countQuery = "select count(p) from Product p order by p.ratingAvg desc")
    Page<Product> findAllOrderByRatingAvg(Pageable pageable);

    @Query(value = "select p from Product p where p.id not in :productIds order by p.ratingAvg desc",
            countQuery = "select count(p) from Product p where p.id not in :productIds order by p.ratingAvg desc")
    Page<Product> findAllOrderByRatingAvg(@Param("productIds") List<Long> productIds,
            Pageable pageable);

    @Query(value = "select p from Product p where p.name like %:keyword% or p.category.name like %:keyword%",
    countQuery = "select count(p) from Product p where p.name like %:keyword% or p.category.name like %:keyword%")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
