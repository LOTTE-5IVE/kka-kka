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

    @Query(value = "select p from Product p "
        + "join fetch p.category "
        + "where p.category.name like %:searchWord%")
    List<Product> findByCategory(@Param("searchWord") String searchWord);

    @Query(value = "select p from Product p "
        + "where p.name like %:searchWord%")
    List<Product> findByName(@Param("searchWord") String searchWord);

    @Query(value = "select p from Product p join fetch p.category where p.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);
}
