package kkakka.mainservice.coupon.domain.repository;

import java.util.List;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("select mc from MemberCoupon mc join fetch mc.coupon where mc.member.id = :memberId and mc.isUsed = false")
    List<MemberCoupon> findAllByMemberIdAndIsUsedFalse(@Param(value = "memberId") Long memberId);

    @Query("select mc from MemberCoupon mc join fetch mc.coupon where mc.member.id = :memberId and mc.isUsed = true")
    List<MemberCoupon> findAllByMemberIdAndIsUsedTrue(@Param(value = "memberId") Long memberId);

    @Query("select mc from MemberCoupon mc JOIN fetch mc.coupon where mc.member.id = :memberId and mc.coupon.id = :couponId "
        + "and mc.isUsed = false and mc.isApply = false")
    MemberCoupon findAllByMemberIdAndIsUsedFalseAndIsApplyFalse(@Param(value = "memberId") Long memberId,
                                                                @Param(value = "couponId") Long couponId);

    List<MemberCoupon> findAllByMemberId(@Param(value = "memberId") Long memberId);

    MemberCoupon findAllByCouponIdAndMemberId(@Param(value = "couponId") Long couponId,
        @Param(value = "memberId") Long memberId);

    List<MemberCoupon> findAllByCouponId(@Param(value = "couponId") Long couponId);

    Long countByCouponIdAndMemberId(@Param(value = "couponId") Long couponId,
        @Param(value = "memberId") Long memberId);

    @Query("select mc from MemberCoupon mc where mc.coupon.priceRule = 'GRADE_COUPON' and mc.member.id = :memberId and mc.isUsed = false")
    List<MemberCoupon> findGradeCouponByMemberId(@Param(value = "memberId") Long memberId);

    int countAllByMemberIdAndIsUsedFalse(@Param(value = "memberId") Long memberId);

}
