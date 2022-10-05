package kkakka.mainservice.coupon.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.MemberCouponId;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.domain.Member;
import kkakka.mainservice.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

  private final CouponRepository couponRepository;
  private final MemberCouponRepository memberCouponRepository;
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final MemberRepository memberRepository;

  /* 관리자 쿠폰 등록 */
  @Transactional
  public List<Long> createCoupon(CouponRequestDto couponRequestDto) {
    List<Long> coupons = new ArrayList<>();
    if (PriceRule.GRADE_COUPON.equals(couponRequestDto.getPriceRule())) {
      List<Member> members = memberRepository.findByGrade(couponRequestDto.getGrade());
      for (Member member : members) {
        Coupon coupon = couponRepository.save(toCouponEntity(couponRequestDto));
        MemberCouponId memberCouponId = new MemberCouponId(member.getId(), coupon.getId());
        MemberCoupon memberCoupon = new MemberCoupon(memberCouponId);
        memberCouponRepository.save(memberCoupon);
        coupons.add(coupon.getId());
      }
      return coupons;
    }

    if (PriceRule.COUPON.equals(couponRequestDto.getPriceRule())) {
      Category category = getCategory(couponRequestDto);
      Product product = getProduct(couponRequestDto);
      Coupon coupon = couponRepository.save(toCouponEntity(couponRequestDto, category, product));
      coupons.add(coupon.getId());
      return coupons;
    }

    throw new IllegalArgumentException();
  }

  @NotNull
  private Coupon toCouponEntity(CouponRequestDto couponRequestDto) {
    return Coupon.create(
        couponRequestDto.getGrade(),
        couponRequestDto.getName(),
        couponRequestDto.getDetail(),
        PriceRule.GRADE_COUPON,
        couponRequestDto.getStartedAt(),
        couponRequestDto.getExpiredAt(),
        couponRequestDto.getPercentage(),
        couponRequestDto.getMaxDiscount(),
        couponRequestDto.getMinOrderPrice());
  }

  @NotNull
  private Coupon toCouponEntity(CouponRequestDto couponRequestDto, Category category,
      Product product) {
    return Coupon.create(
        category,
        product,
        couponRequestDto.getName(),
        couponRequestDto.getDetail(),
        PriceRule.COUPON,
        couponRequestDto.getStartedAt(),
        couponRequestDto.getExpiredAt(),
        couponRequestDto.getPercentage(),
        couponRequestDto.getMaxDiscount(),
        couponRequestDto.getMinOrderPrice()
    );
  }

  private Product getProduct(CouponRequestDto couponRequestDto) {
    return productRepository.findById(couponRequestDto.getProductId())
        .orElseThrow(IllegalArgumentException::new);
  }

  private Category getCategory(CouponRequestDto couponRequestDto) {
    Category category = categoryRepository.findById(couponRequestDto.getCategoryId())
        .orElseThrow(IllegalArgumentException::new);
    return category;
  }

  /* 관리자 쿠폰 사용 (삭제) */
  @Transactional
  public void useCoupon(Long couponId) {
    Coupon coupon = couponRepository.findById(couponId).orElseThrow(IllegalArgumentException::new);
    coupon.useCoupon();
    couponRepository.save(coupon);
  }

  /* 관리자 모든 쿠폰 조회 */
  public List<Coupon> findAllCoupons() {
    return couponRepository.findAll();
  }

  /* 사용자 쿠폰 다운로드 */
  @Transactional
  public void downloadCoupon(Long couponId, Long memberId) {
    // 비회원일 경우
    if (memberId == null) {
      return;
    }
    if (checkExpiredDate(couponId)) {
      MemberCouponId memberCouponId = new MemberCouponId(memberId, couponId);
      MemberCoupon memberCoupon = new MemberCoupon(memberCouponId);
      memberCouponRepository.save(memberCoupon);
    }
  }

  /* 유효기간 체크 */
  public boolean checkExpiredDate(Long couponId) {
    Coupon coupon = couponRepository.findById(couponId).orElseThrow(IllegalArgumentException::new);
    if (coupon.getStartedAt().isBefore(LocalDateTime.now())
        && coupon.getExpiredAt().isAfter(LocalDateTime.now())) {
      return true;
    }
    return false;
  }

  /* 사용자 쿠폰 사용 */

  /* 사용자 다운 가능한 쿠폰 목록 조회 */

}