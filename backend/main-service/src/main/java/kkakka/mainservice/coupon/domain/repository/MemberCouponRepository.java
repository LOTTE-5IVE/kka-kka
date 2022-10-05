package kkakka.mainservice.coupon.domain.repository;

import java.util.List;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.MemberCouponId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, MemberCouponId> {

  @Query("select mc from MemberCoupon mc where mc.memberCouponId.memberId = :memberId")
  List<MemberCoupon> findAllByMemberId(@Param(value = "memberId") Long memberId);
}
