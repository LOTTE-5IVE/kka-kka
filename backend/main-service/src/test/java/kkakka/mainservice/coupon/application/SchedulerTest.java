package kkakka.mainservice.coupon.application;

import static kkakka.mainservice.fixture.TestDataLoader.PRODUCT_1;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.coupon.domain.Discount;
import kkakka.mainservice.coupon.domain.DiscountType;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.repository.DiscountRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.coupon.ui.dto.DiscountRequestDto;
import kkakka.mainservice.coupon.util.PromotionScheduler;
import kkakka.mainservice.member.member.domain.Grade;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.order.domain.Order;
import kkakka.mainservice.order.domain.ProductOrder;
import kkakka.mainservice.order.domain.repository.OrderRepository;
import kkakka.mainservice.order.domain.repository.ProductOrderRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SchedulerTest extends TestContext {

    @Autowired
    DiscountService discountService;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductOrderRepository productOrderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CouponService couponService;
    @Autowired
    MemberCouponRepository memberCouponRepository;

    @DisplayName("할인 만료 테스트")
    @Test
    void checkDiscountExpired_success() {
        // given
        Long discountId1 = discountService.createDiscount(new DiscountRequestDto(
            null, PRODUCT_1.getId(), "test", 10, DiscountType.PRODUCT_DISCOUNT.name(),
            LocalDateTime.of(2022, 1, 1, 0, 0),
            LocalDateTime.of(2025, 11, 1, 0, 0)));

        Long discountId2 = discountService.createDiscount(new DiscountRequestDto(
            null, PRODUCT_1.getId(), "test", 10, DiscountType.PRODUCT_DISCOUNT.name(),
            LocalDateTime.of(2020, 1, 1, 0, 0),
            LocalDateTime.of(2025, 11, 5, 0, 0)));

        Discount discount1 = discountRepository.findById(discountId1)
            .orElseThrow(KkaKkaException::new);
        Discount discount2 = discountRepository.findById(discountId2)
            .orElseThrow(KkaKkaException::new);

        // when
        List<Discount> discounts = discountRepository.findAll();
        for (Discount discount : discounts) {
            if (LocalDateTime.now().isAfter(discount.getExpiredAt())) {
                discountService.deleteDiscount(discount.getId());
            }
        }

        // then
        Assertions.assertThat(discount1.getIsDeleted()).isEqualTo(false);
        Assertions.assertThat(discount2.getIsDeleted()).isEqualTo(false);
    }

    @DisplayName("자동 등업 테스트")
    @Test
    void autoGradeUp_success() {
        // given
        Member member = Member.create(null, "testName", "test@com", "1234", "ageGroup");
        memberRepository.save(member);
        ProductOrder productOrder = ProductOrder.create(PRODUCT_1, 15000, 3);
        productOrderRepository.save(productOrder);
        Order order = Order.create(member, null, 15000, List.of(productOrder));
        orderRepository.save(order);

        // when
        List<Member> members = memberRepository.findByGrade(Grade.BRONZE);
        for (Member mem : members) {
            int totalPrice = orderRepository.findTotalPriceByMemberId(member.getId())
                .orElseGet(() -> 0);
            if (totalPrice > 10000) {
                mem.gradeUp(Grade.SILVER);
                memberRepository.save(mem);
            }
        }
        Long couponId = createGradeCoupon(Grade.SILVER, 20, 2000, 10000);
        MemberCoupon memberCoupon = memberCouponRepository.findMemberCouponByCouponIdAndMemberId(
            couponId,
            member.getId());

        // then
        Assertions.assertThat(member.getGrade()).isEqualTo(Grade.SILVER);
        Assertions.assertThat(member.getGrade()).isEqualTo(Grade.SILVER);
        Assertions.assertThat(memberCoupon.getCoupon().getName()).isEqualTo("정기 등급 쿠폰");
    }

    private Long createGradeCoupon(Grade grade, int percentage, int maxDiscount,
        int minOrderPrice) {
        LocalDateTime now = LocalDateTime.now();
        Long couponId = couponService.createCoupon(new CouponRequestDto(
            null, grade, null, "정기 등급 쿠폰", "GRADE_COUPON",
            LocalDateTime.now(),
            LocalDateTime.of(now.getYear(), now.getMonthValue(),
                YearMonth.of(now.getYear(), now.getMonthValue()).lengthOfMonth(), 11, 59),
            percentage, maxDiscount, minOrderPrice
        ));
        return couponId;
    }
}
