package kkakka.mainservice.review.domain.repository;

import java.util.Optional;
import kkakka.mainservice.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r from Review r where r.product.id = :productId",
            countQuery = "select count(r) from Review r where r.product.id = :productId")
    Page<Review> findAllByProductId(@Param(value = "productId") Long productId, Pageable pageable);

    @Query("select r from Review r where r.member.id = :memberId and r.product.id = :productId")
    Optional<Review> findByMemberIdAndProductId(@Param(value = "memberId") Long memberId,
            @Param("productId") Long productId);
}
