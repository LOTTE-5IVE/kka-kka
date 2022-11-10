package kkakka.mainservice.coupon.domain.repository;

import java.util.List;
import kkakka.mainservice.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.product.id = :productId and c.isDeleted = false")
    List<Coupon> findCouponsByProductIdAndNotDeleted(@Param(value = "productId") Long productId);

    @Query("select c from Coupon c where c.isDeleted = false")
    List<Coupon> findAllCouponsNotDeleted();

    @Query("select c from Coupon c where c.category.id = :categoryId and c.isDeleted = false")
    List<Coupon> findCouponsByCategoryIdAndNotDeleted(@Param(value = "categoryId") Long categoryId);
}
