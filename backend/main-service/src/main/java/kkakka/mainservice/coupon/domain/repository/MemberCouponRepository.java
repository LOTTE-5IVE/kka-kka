package kkakka.mainservice.coupon.domain.repository;

import java.util.List;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("select mc from MemberCoupon mc join fetch mc.coupon where mc.member.id = :memberId and mc.isUsed = false")
    List<MemberCoupon> findAllByMemberId(@Param(value = "memberId") Long memberId);

    @Query("select mc.coupon from MemberCoupon mc where mc.member.id = :memberId")
    List<Coupon> findDownloadedCouponsByMemberId(@Param(value = "memberId") Long memberId);

    @Query("select mc from MemberCoupon mc where mc.coupon.id = :couponId")
    MemberCoupon findMemberCouponByCouponId(@Param(value = "couponId") Long couponId);

}
