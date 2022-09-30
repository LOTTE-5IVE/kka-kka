package kkakka.mainservice.coupon.application;

import java.util.List;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.domain.Grade;
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

  /* 쿠폰 등록 */
  @Transactional
  public Long createCoupon(CouponRequestDto couponRequestDto) {
    if (PriceRule.GRADE_COUPON.equals(couponRequestDto.getPriceRule())) {
      Coupon coupon = couponRepository.save(toCouponEntity(couponRequestDto));
      // TODO : MemberCoupon에 저장

      return coupon.getId();
    }

    if (PriceRule.COUPON.equals(couponRequestDto.getPriceRule())) {
      Category category = getCategory(couponRequestDto);
      Product product = getProduct(couponRequestDto);
      Coupon coupon = couponRepository.save(toCouponEntity(couponRequestDto, category, product));
      return coupon.getId();
    }

    throw new IllegalArgumentException();
  }

  @NotNull
  private Coupon toCouponEntity(CouponRequestDto couponRequestDto) {
    return Coupon.create(
        Grade.valueOf(couponRequestDto.getGrade()),
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

  /* 관리자 쿠폰 사용 */
  @Transactional
  public void useCoupon(Long couponId) {
    Coupon coupon = couponRepository.findById(couponId).orElseThrow(IllegalArgumentException::new);
    coupon.useCoupon();
    couponRepository.save(coupon);
  }

  /* 관리자 모든 쿠폰 조회 */
  public List<Coupon> findCoupons() {
    return couponRepository.findAll();
  }

  /* 쿠폰 다운로드 */
  public void downloadCoupon(Long couponId, Long memberId) {
    // TODO : 비회원일 경우
  }
}
