package kkakka.mainservice.coupon.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.common.auth.AuthenticationPrincipal;
import kkakka.mainservice.common.auth.LoginMember;
import kkakka.mainservice.common.auth.aop.AdminOnly;
import kkakka.mainservice.common.auth.aop.MemberOnly;
import kkakka.mainservice.coupon.application.CouponService;
import kkakka.mainservice.coupon.application.DiscountService;
import kkakka.mainservice.coupon.ui.dto.CouponProductDto;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.coupon.ui.dto.CouponResponseDto;
import kkakka.mainservice.coupon.ui.dto.DiscountRequestDto;
import kkakka.mainservice.coupon.ui.dto.DiscountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final DiscountService discountService;

    /* 쿠폰 등록 */
    @AdminOnly
    @PostMapping
    public ResponseEntity<Void> createCoupon(
        @RequestBody CouponRequestDto couponRequestDto,
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long couponId = couponService.createCoupon(couponRequestDto);
        return ResponseEntity.created(URI.create(couponId.toString())).build();
    }

    /* 쿠폰 삭제 */
    @AdminOnly
    @PutMapping("/{couponId}")
    public ResponseEntity<Void> deleteCouponByAdmin(
        @PathVariable Long couponId,
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        couponService.deleteCouponByCouponId(couponId);
        return ResponseEntity.ok().build();
    }

    /* 쿠폰 조회 */
    @AdminOnly
    @GetMapping
    public ResponseEntity<List<CouponResponseDto>> findAllCoupons(
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<CouponResponseDto> couponResponseDto = couponService.showAllCouponsNotDeleted();
        return ResponseEntity.status(HttpStatus.OK).body(couponResponseDto);
    }

    /* 다운로드 가능한 쿠폰 조회 */
    @MemberOnly
    @GetMapping("/download")
    public ResponseEntity<List<CouponResponseDto>> findDownloadableCoupons(
        @AuthenticationPrincipal LoginMember loginMember) {
        List<CouponResponseDto> coupons = couponService.findDownloadableCoupons(
            loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /* 사용자의 쿠폰 다운로드 */
    @MemberOnly
    @PostMapping("/download/{couponId}")
    public ResponseEntity<Void> downloadCoupon(@PathVariable Long couponId,
        @AuthenticationPrincipal LoginMember loginMember) {
        couponService.downloadCoupon(couponId, loginMember.getId());
        return ResponseEntity.ok().build();
    }

    /* 사용 가능한 쿠폰 조회 */
    @MemberOnly
    @GetMapping("/me/available")
    public ResponseEntity<List<CouponResponseDto>> findUsableCoupons(
        @AuthenticationPrincipal LoginMember loginMember) {
        List<CouponResponseDto> coupons = couponService.findUsableCoupons(loginMember.getId())
            .stream()
            .map(coupon -> CouponResponseDto.create(coupon))
            .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /* 사용한 쿠폰 조회 */
    @MemberOnly
    @GetMapping("/me/unavailable")
    public ResponseEntity<List<CouponResponseDto>> findUsedCoupons(
        @AuthenticationPrincipal LoginMember loginMember) {
        List<CouponResponseDto> coupons = couponService.findUsedCoupons(loginMember.getId())
            .stream()
            .map(coupon -> CouponResponseDto.create(coupon))
            .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(coupons);
    }

    /* 할인 등록 */
    @AdminOnly
    @PostMapping("/discount")
    public ResponseEntity<Long> createDiscount(
        @RequestBody DiscountRequestDto discountRequestDto,
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        Long discountId = discountService.createDiscount(discountRequestDto);
        return ResponseEntity.created(URI.create(discountId.toString())).build();
    }

    /* 할인 삭제 */
    @AdminOnly
    @PutMapping("/discount/{discountId}")
    public ResponseEntity<Void> deleteDiscount(
        @PathVariable Long discountId,
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.ok().build();
    }

    /* 할인 조회 */
    @AdminOnly
    @GetMapping("/discount")
    public ResponseEntity<List<DiscountResponseDto>> showAllDiscounts(
        @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<DiscountResponseDto> discounts = discountService.showAllDiscountsNotDeleted();
        return ResponseEntity.status(HttpStatus.OK).body(discounts);
    }

    /* 상품, 멤버에 대해 적용 가능한 쿠폰 조회 */
    @MemberOnly
    @GetMapping("/me/products/{productId}")
    public ResponseEntity<List<CouponProductDto>> showCouponsByProductIdAndMemberId(
        @PathVariable Long productId, @AuthenticationPrincipal LoginMember loginMember) {
        List<CouponProductDto> couponResponseDtos = couponService.showCouponsByProductIdAndMemberId(
            productId, loginMember.getId());
        return ResponseEntity.status(HttpStatus.OK).body(couponResponseDtos);
    }

    /* 상품에 대해 적용 가능한 쿠폰 조회 */
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<CouponProductDto>> showCouponsByProductId(
        @PathVariable Long productId) {
        List<CouponProductDto> couponResponseDtos = couponService.showCouponsByProductId(
            productId);
        return ResponseEntity.status(HttpStatus.OK).body(couponResponseDtos);
    }

    /* 사용자의 상품 쿠폰 다운로드 */
    @MemberOnly
    @PostMapping("/{productId}/{couponId}")
    public ResponseEntity<List<CouponProductDto>> downloadAndShowProductCoupons(
        @PathVariable Long couponId,
        @AuthenticationPrincipal LoginMember loginMember, @PathVariable Long productId) {
        List<CouponProductDto> couponProductDtos = couponService.downloadProductCoupon(couponId,
            loginMember.getId(), productId);
        return ResponseEntity.status(HttpStatus.OK).body(couponProductDtos);
    }
}
