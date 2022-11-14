package kkakka.mainservice.coupon.util;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.application.DiscountService;
import kkakka.mainservice.coupon.domain.Discount;
import kkakka.mainservice.coupon.domain.repository.DiscountRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.member.domain.Grade;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionScheduler {

    private final DiscountRepository discountRepository;
    private final DiscountService discountService;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final CouponService couponService;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void checkDiscountExpiredDateSchedule() {
        List<Discount> discounts = discountRepository.findAllDiscountsNotDeleted();
        for (Discount discount : discounts) {
            if (LocalDateTime.now().isAfter(discount.getExpiredAt())) {
                discountService.deleteDiscount(discount.getId());
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * *")
    public void updateGradeSchedule() {
        updateGradeByTotalPriceToAllMembers();
        createGradeCouponToAllMembers();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateGradeByTotalPriceToAllMembers() {
        updateGradeByTotalPrice(findMembersByGrade(Grade.BRONZE), 10000, Grade.SILVER);
        updateGradeByTotalPrice(findMembersByGrade(Grade.SILVER), 20000, Grade.GOLD);
        updateGradeByTotalPrice(findMembersByGrade(Grade.GOLD), 30000, Grade.VIP);
        updateGradeByTotalPrice(findMembersByGrade(Grade.VIP), 40000, Grade.VIP);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createGradeCouponToAllMembers() {
        createGradeCoupon(Grade.BRONZE, 10, 1000, 10000);
        createGradeCoupon(Grade.SILVER, 20, 2000, 10000);
        createGradeCoupon(Grade.GOLD, 30, 3000, 10000);
        createGradeCoupon(Grade.VIP, 40, 4000, 10000);
    }

    private List<Member> findMembersByGrade(Grade grade) {
        return memberRepository.findByGrade(grade);
    }

    private void updateGradeByTotalPrice(List<Member> members, int price, Grade grade) {
        for (Member member : members) {
            int totalPrice = orderRepository.findTotalPriceByMemberId(member.getId())
                .orElseGet(() -> 0);
            if (totalPrice > price) {
                member.gradeUp(grade);
                memberRepository.save(member);
            }
        }
    }

    private void createGradeCoupon(Grade grade, int percentage, int maxDiscount,
        int minOrderPrice) {
        LocalDateTime now = LocalDateTime.now();
        couponService.createCoupon(new CouponRequestDto(
            null, grade, null, "정기 등급 쿠폰", "GRADE_COUPON",
            LocalDateTime.now(),
            LocalDateTime.of(now.getYear(), now.getMonthValue(),
                YearMonth.of(now.getYear(), now.getMonthValue()).lengthOfMonth(), 11, 59),
            percentage, maxDiscount, minOrderPrice
        ));
    }
}
