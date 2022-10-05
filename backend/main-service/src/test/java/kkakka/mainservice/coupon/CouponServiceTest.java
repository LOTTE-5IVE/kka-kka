package kkakka.mainservice.coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.domain.Grade;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CouponServiceTest {

  @Autowired
  CouponService couponService;
  @Autowired
  CouponRepository couponRepository;
  @Autowired
  ProductRepository productRepository;
  @Autowired
  MemberCouponRepository memberCouponRepository;
  @Autowired
  MemberRepository memberRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Test
  @DisplayName("쿠폰 사용 여부 확인 - 성공")
  void useCouponTest_success() {
    Coupon coupon = Coupon.create(
        null,
        null,
        "과자쿠폰",
        "15% 할인",
        PriceRule.COUPON,
        LocalDateTime.now(),
        LocalDateTime.now(),
        15,
        1000,
        2000
    );

    couponRepository.save(coupon);
    couponService.useCoupon(coupon.getId());

    assertThat(coupon.getIsUsed()).isEqualTo(1);
  }

  @DisplayName("등급쿠폰 생성 테스트 - 성공")
  @Test
  void createGradeCoupon_success() {
    // given
    Member member1 = new Member(Grade.GOLD);
    Member member2 = new Member(Grade.GOLD);
    Member member3 = new Member(Grade.BRONZE);
    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);

    List<Member> members = Arrays.asList(member1, member2, member3);

    // when
    couponService.createCoupon(new CouponRequestDto(
        null,
        null,
        Grade.GOLD,
        null,
        "test",
        "testCoupon",
        "GRADE_COUPON",
        LocalDateTime.now(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        10,
        2000, 10000, 0
    ));

    // then
    List<MemberCoupon> memberCoupons = memberCouponRepository.findAll();
    List<Long> savedCouponMemberIds = memberCoupons.stream()
        .mapToLong(memberCoupon -> memberCoupon.getMemberCouponId().getMemberId())
        .boxed()
        .collect(Collectors.toList());

    List<Long> originMemberIds = members.stream()
        .filter(member -> member.getGrade().equals(Grade.GOLD))
        .mapToLong(Member::getId)
        .boxed()
        .collect(Collectors.toList());

    assertThat(savedCouponMemberIds).hasSameElementsAs(originMemberIds);
  }

  @DisplayName("쿠폰 다운로드 테스트 - 성공")
  @Test
  void downloadCoupon_success() {
    // given
    Member member = new Member();
    Coupon coupon = Coupon.create(
        null,
        null,
        "testCoupon",
        "test 입니다",
        PriceRule.COUPON,
        LocalDateTime.of(2020, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15,
        1000,
        2000
    );
    memberRepository.save(member);
    couponRepository.save(coupon);

    // when
    couponService.downloadCoupon(coupon.getId(), member.getId());

    // then
    assertThat(memberCouponRepository.findAll().size()).isEqualTo(1);
  }

  @DisplayName("사용가능한 쿠폰 조회 - 성공")
  @Test
  void findUsableCoupons_success() {
    Member member = new Member();
    Coupon coupon1 = Coupon.create(
        null,
        null,
        "testCoupon",
        "test 입니다",
        PriceRule.COUPON,
        LocalDateTime.of(2020, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15,
        1000,
        2000
    );
    Coupon coupon2 = Coupon.create(
        null,
        null,
        "testCoupon",
        "test 입니다",
        PriceRule.COUPON,
        LocalDateTime.of(2020, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15,
        1000,
        2000
    );
    memberRepository.save(member);
    couponRepository.save(coupon1);
    couponRepository.save(coupon2);

    couponService.downloadCoupon(coupon1.getId(), member.getId());
    couponService.downloadCoupon(coupon2.getId(), member.getId());

    List<Coupon> coupons = couponService.findUsableCoupons(member.getId());

    assertThat(coupons.size()).isEqualTo(2);
  }

  @DisplayName("다운가능한 쿠폰 조회 - 성공")
  @Test
  void findDownloadCoupons_success() {
    Member member = new Member();
    Coupon coupon1 = Coupon.create(
        null, null, "testCoupon", "test 입니다", PriceRule.COUPON,
        LocalDateTime.of(2024, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15, 1000, 2000
    );
    Coupon coupon2 = Coupon.create(
        null, null, "testCoupon", "test 입니다", PriceRule.COUPON,
        LocalDateTime.of(2020, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15, 1000, 2000
    );
    Coupon coupon3 = Coupon.create(
        Grade.VIP,
        "testCoupon", "test입니다", PriceRule.GRADE_COUPON,
        LocalDateTime.of(2020, 3, 16, 3, 16),
        LocalDateTime.of(2025, 3, 16, 3, 16),
        15, 1000, 2000
    );
    memberRepository.save(member);
    couponRepository.save(coupon1);
    couponRepository.save(coupon2);
    couponRepository.save(coupon3);

    couponService.downloadCoupon(coupon1.getId(), member.getId());
    couponService.downloadCoupon(coupon2.getId(), member.getId());
    couponService.downloadCoupon(coupon3.getId(), member.getId());

    List<Coupon> coupons = couponService.findDownloadCoupons();

    assertThat(coupons.size()).isEqualTo(1);
  }
}
