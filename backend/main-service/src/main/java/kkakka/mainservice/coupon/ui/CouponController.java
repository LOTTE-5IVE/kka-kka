package kkakka.mainservice.coupon.ui;

import java.net.URI;
import java.util.List;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.member.auth.ui.AuthenticationPrincipal;
import kkakka.mainservice.member.auth.ui.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /* TODO : 할인 등록 */
    /* TODO : return 값 */

    /* 쿠폰 등록 */
    @PostMapping
    public ResponseEntity<Void> createCoupon(
        @RequestBody CouponRequestDto couponRequestDto
    ) {
        List<Long> couponId = couponService.createCoupon(couponRequestDto);
        return ResponseEntity.created(URI.create(couponId.toString())).build();
    }

    /* 쿠폰 사용 - 삭제 */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> useCouponByAdmin(@PathVariable Long couponId) {
        couponService.useCoupon(couponId);
        return ResponseEntity.ok().build();
    }

    /* 쿠폰 조회 */
    @GetMapping
    public ResponseEntity<List<Coupon>> findAllCoupons() {
        List<Coupon> coupons = couponService.findAllCoupons();
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /* 다운로드 가능한 쿠폰 조회 */
    @GetMapping("/download")
    public ResponseEntity<List<Coupon>> findDownloadableCoupons(
        @AuthenticationPrincipal LoginMember loginMember) {
        List<Coupon> coupons = couponService.findDownloadableCoupons(loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /* 사용자의 쿠폰 다운로드 */
    @PostMapping("/download/{couponId}")
    public ResponseEntity<Void> downloadCoupon(@PathVariable Long couponId,
        @AuthenticationPrincipal LoginMember loginMember) {
        couponService.downloadCoupon(couponId, loginMember.getId());
        return ResponseEntity.ok().build();
    }

    /* 사용 가능한 쿠폰 조회 */
    @GetMapping("/me")
    public ResponseEntity<List<Coupon>> findUsableCoupons(
        @AuthenticationPrincipal LoginMember loginMember) {
        List<Coupon> coupons = couponService.findUsableCoupons(loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }
}
