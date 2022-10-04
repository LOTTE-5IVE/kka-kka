package kkakka.mainservice.coupon.domain.repository;

import kkakka.mainservice.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

}
