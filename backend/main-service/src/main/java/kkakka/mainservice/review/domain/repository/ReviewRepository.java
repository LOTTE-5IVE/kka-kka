package kkakka.mainservice.review.domain.repository;

import java.util.List;
import kkakka.mainservice.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.product.id = :productId")
    List<Review> findAllByProductId(@Param(value = "productId") Long productId);
}
