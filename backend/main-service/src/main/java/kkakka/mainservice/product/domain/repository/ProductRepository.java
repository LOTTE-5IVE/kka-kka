package kkakka.mainservice.product.domain.repository;

import java.util.List;
import kkakka.mainservice.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select p from Product p "
        + "join fetch p.category "
        + "where p.category.name like %:searchWord%")
    List<Product> findByCategory(@Param("searchWord") String searchWord);

    @Query(value = "select p from Product p "
        + "where p.name like %:searchWord%")
    List<Product> findByName(@Param("searchWord") String searchWord);
}
