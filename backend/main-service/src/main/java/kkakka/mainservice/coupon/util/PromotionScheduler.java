package kkakka.mainservice.coupon.util;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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

@Component
@RequiredArgsConstructor
public class PromotionScheduler {

    private final DiscountRepository discountRepository;
    private final DiscountService discountService;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final CouponService couponService;

    @Scheduled(cron = "0 0 0 * * *")
    public void checkDiscountExpiredDateSchedule() {
        List<Discount> discounts = discountRepository.findAll();
        for (Discount discount : discounts) {
            if (LocalDateTime.now().isBefore(discount.getExpiredAt())) {
                discountService.deleteDiscount(discount.getId());
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void updateGradeSchedule() {
        updateGradeByTotalPrice(findMembersByGrade(Grade.BRONZE), 10000, Grade.SILVER);
        updateGradeByTotalPrice(findMembersByGrade(Grade.SILVER), 20000, Grade.GOLD);
        updateGradeByTotalPrice(findMembersByGrade(Grade.GOLD), 30000, Grade.VIP);
        updateGradeByTotalPrice(findMembersByGrade(Grade.VIP), 40000, Grade.VIP);
        createGradeCoupon(Grade.BRONZE);
        createGradeCoupon(Grade.SILVER);
        createGradeCoupon(Grade.GOLD);
        createGradeCoupon(Grade.VIP);
    }

    private List<Member> findMembersByGrade(Grade grade) {
        return memberRepository.findByGrade(grade);
    }

    private void updateGradeByTotalPrice(List<Member> members, int price, Grade grade) {
        for (Member member : members) {
            Optional<Integer> totalPrice = orderRepository.findOrderPriceByMemberId(member.getId());
            if (totalPrice.get() > price) {
                member.updateGrade(grade);
                memberRepository.save(member);
            }
        }
    }

    private void createGradeCoupon(Grade grade) {
        LocalDateTime now = LocalDateTime.now();
        Calendar cal = Calendar.getInstance();
        couponService.createCoupon(new CouponRequestDto(
            null, grade, null, "정기 등급 쿠폰", "GRADE_COUPON",
            LocalDateTime.now(),
            LocalDateTime.of(now.getYear(), now.getMonthValue(),
                cal.getActualMaximum(now.getMonthValue()), 0, 0),
            10, 2000, 2000
        ));
    }
}
