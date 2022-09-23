package kkakka.mainservice.product.domain.repository;

import kkakka.mainservice.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    Product findByPId(Long id);
}
