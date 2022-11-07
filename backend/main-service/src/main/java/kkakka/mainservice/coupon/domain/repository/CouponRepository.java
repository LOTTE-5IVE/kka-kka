package kkakka.mainservice.coupon.domain.repository;

import kkakka.mainservice.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.product.id = :productId or c.category.id = :categoryId and c.isDeleted = false ")
    List<Coupon> findCouponsByProductIdAndNotDeleted(@Param(value = "productId") Long productId,
                                                     @Param(value = "categoryId") Long categoryId);

    @Query("select c from Coupon c where c.isDeleted = false")
    List<Coupon> findAllCouponsNotDeleted();

}
