package kkakka.mainservice.coupon.application;

import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.coupon.ui.dto.CouponResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    /* 쿠폰 등록 */
    public Long createCoupon(CouponRequestDto couponRequestDto) {
        if(couponRequestDto.getPriceRule().equals("GRADE_COUPON")) {
            // 등급 쿠폰

        } else if(couponRequestDto.getPriceRule().equals("COUPON")) {
            // 일반 쿠폰
        }
        return 1L;
    }

    /* 관리자 쿠폰 삭제 */
    public String deleteCoupon(Long couponId){
        couponRepository.deleteById(couponId);
        return "";
    }

    /* 관리자 모든 쿠폰 조회 */
    public List<Coupon> findCoupons() {
        return couponRepository.findAll();
    }

    // 멤버별 사용할 수 있는 쿠폰 조회


}
