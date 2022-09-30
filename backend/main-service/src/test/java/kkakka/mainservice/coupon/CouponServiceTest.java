package kkakka.mainservice.coupon;

import java.time.LocalDateTime;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CouponServiceTest {

  @Autowired
  CouponService couponService;
  @Autowired
  CouponRepository couponRepository;
  @Autowired
  ProductRepository productRepository;

  @Test
  @Transactional
  @DisplayName("쿠폰 사용 여부 확인 - 성공")
  public void useCouponTest_success() {
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

    Assertions.assertThat(coupon.getIsUsed()).isEqualTo(1);
  }
}
