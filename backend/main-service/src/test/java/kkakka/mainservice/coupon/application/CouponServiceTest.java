package kkakka.mainservice.coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kkakka.mainservice.TestContext;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponProductResponseDto;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.coupon.ui.dto.CouponResponseDto;
import kkakka.mainservice.member.member.domain.Grade;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CouponServiceTest extends TestContext {

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
        // given
        Member member = new Member();
        Coupon coupon = Coupon.create(
            null, null, "testCoupon",
            PriceRule.COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        couponRepository.save(coupon);
        memberRepository.save(member);

        // when
        couponService.downloadCoupon(coupon.getId(), member.getId());
        couponService.useCouponByMember(coupon.getId(), member.getId());
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllMemberCouponByCouponId(
            coupon.getId());

        // then
        assertThat(memberCoupons.get(0).getIsUsed()).isEqualTo(true);
    }

    @DisplayName("등급쿠폰 생성 테스트 - 성공")
    @Test
    void createGradeCoupon_success() {
        // given
        Member member1 = Member.create(null, null, "이름", "abc@abc.com", "1234", "서울시", "10대",
            Grade.GOLD);
        Member member2 = Member.create(null, null, "이름", "abc@abc.com", "1234", "서울시", "10대",
            Grade.GOLD);
        Member member3 = Member.create(null, null, "이름", "abc@abc.com", "1234", "서울시", "10대",
            Grade.VIP);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<Member> members = Arrays.asList(member1, member2, member3);

        // when
        couponService.createCoupon(new CouponRequestDto(
            null, Grade.GOLD, null,
            "test", "GRADE_COUPON",
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            10, 2000, 10000
        ));

        // then
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAll();
        List<Long> savedCouponMemberIds = memberCoupons.stream()
            .mapToLong(memberCoupon -> memberCoupon.getMember().getId())
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
        // given
        Member member = new Member();
        Coupon coupon1 = Coupon.create(
            null, null,
            "testCoupon",
            PriceRule.COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        Coupon coupon2 = Coupon.create(
            null, null,
            "testCoupon",
            PriceRule.COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        memberRepository.save(member);
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);

        // when
        couponService.downloadCoupon(coupon1.getId(), member.getId());
        couponService.downloadCoupon(coupon2.getId(), member.getId());

        List<Coupon> coupons = couponService.findUsableCoupons(member.getId());

        // then
        assertThat(coupons.size()).isEqualTo(2);
    }

    @DisplayName("다운가능한 쿠폰 조회 - 성공")
    @Test
    void findDownloadCoupons_success() {
        // given
        Member member = new Member();
        Coupon coupon1 = Coupon.create(
            null, null, "testCoupon", PriceRule.COUPON,
            LocalDateTime.of(2024, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        Coupon coupon2 = Coupon.create(
            null, null, "testCoupon", PriceRule.COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        Coupon coupon3 = Coupon.create(
            Grade.VIP,
            "testCoupon", PriceRule.GRADE_COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        memberRepository.save(member);
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        couponRepository.save(coupon3);

        // when
        List<CouponResponseDto> coupons = couponService.findDownloadableCoupons(member.getId());

        // then
        assertThat(coupons.size()).isEqualTo(1);
    }

    @DisplayName("상품 다운 가능한 쿠폰 조회 - 성공")
    @Test
    void showDownloadableCoupons() {
        // given
        Member member = new Member();
        Product product = new Product(null, null, "test", 1000, 20,
            "", "", "", null);
        Coupon coupon1 = Coupon.create(
            null, product, "testCoupon", PriceRule.COUPON,
            LocalDateTime.of(2022, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        Coupon coupon2 = Coupon.create(
            null, product, "testCoupon", PriceRule.COUPON,
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            15, 1000, 2000
        );
        memberRepository.save(member);
        productRepository.save(product);
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);

        // when
        List<CouponProductResponseDto> couponProductResponseDtos = couponService.showCouponsByProductIdAndMemberId(
            product.getId(), member.getId());

        // then
        assertThat(couponProductResponseDtos.size()).isEqualTo(2);
    }

    @DisplayName("등급쿠폰 금액할인 생성 테스트 - 성공")
    @Test
    void createMoneyGradeCoupon_success() {
        // given
        // when
        couponService.createCoupon(new CouponRequestDto(
            null, Grade.GOLD, null,
            "test", "GRADE_COUPON",
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            null, 2000, 10000
        ));

        // then
        assertThat(couponRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("일반쿠폰 금액할인 생성 테스트 - 성공")
    @Test
    void createMoneyCoupon_success() {
        // given
        Product product = new Product();
        // when
        couponService.createCoupon(new CouponRequestDto(
            null, null, product.getId(),
            "test", "COUPON",
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            null, 2000, 10000
        ));

        // then
        assertThat(couponRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("상품 적용 가능한 쿠폰 조회 - 성공")
    @Test
    void showProductCouponsByProductId() {
        // given
        Product product = new Product(null, null, "product",
            1000, 20, "", "", "", null);
        productRepository.save(product);
        couponService.createCoupon(new CouponRequestDto(
            null, null, product.getId(),
            "test", "COUPON",
            LocalDateTime.of(2020, 3, 16, 3, 16),
            LocalDateTime.of(2025, 3, 16, 3, 16),
            null, 2000, 10000
        ));

        // when
        List<CouponProductResponseDto> couponProductResponseDtos = couponService.showCouponsByProductId(
            product.getId());

        // then
        assertThat(couponProductResponseDtos.size()).isEqualTo(1);
    }
}
