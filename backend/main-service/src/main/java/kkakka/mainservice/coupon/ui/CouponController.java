package kkakka.mainservice.coupon.ui;

import java.net.URI;
import java.util.List;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

  private final CouponService couponService;

  /* 쿠폰 등록 */
  @PostMapping
  public ResponseEntity<Void> createCoupon(
      @RequestBody CouponRequestDto couponRequestDto
  ) {
    List<Long> couponId = couponService.createCoupon(couponRequestDto);
    return ResponseEntity.created(URI.create(couponId.toString())).build();
  }

  /* TODO : 할인 등록 */

  /* 쿠폰 사용 - 삭제 */
  @DeleteMapping("/{couponId}")
  public ResponseEntity<Void> useCoupon(@PathVariable Long couponId) {
    couponService.useCoupon(couponId);
    return ResponseEntity.ok().build();
  }

  /* 쿠폰 조회 */
  @GetMapping
  public ResponseEntity<Void> findCoupons() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/download")
  public ResponseEntity<Void> downloadCoupon() {
    return ResponseEntity.ok().build();
  }

}
