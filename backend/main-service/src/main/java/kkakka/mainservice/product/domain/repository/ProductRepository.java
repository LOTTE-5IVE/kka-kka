package kkakka.mainservice.product.domain.repository;

import kkakka.mainservice.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Long countBy();
    Page<Product> findAll(Pageable pageable);
}
