package kkakka.mainservice.coupon.ui;

import java.util.List;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

}
