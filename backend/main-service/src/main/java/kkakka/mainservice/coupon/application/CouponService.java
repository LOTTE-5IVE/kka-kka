package kkakka.mainservice.coupon.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kkakka.mainservice.category.domain.Category;
import kkakka.mainservice.category.domain.repository.CategoryRepository;
import kkakka.mainservice.common.exception.InvalidCouponRequestException;
import kkakka.mainservice.common.exception.KkaKkaException;
import kkakka.mainservice.coupon.domain.Coupon;
import kkakka.mainservice.coupon.domain.MemberCoupon;
import kkakka.mainservice.coupon.domain.PriceRule;
import kkakka.mainservice.coupon.domain.repository.CouponRepository;
import kkakka.mainservice.coupon.domain.repository.MemberCouponRepository;
import kkakka.mainservice.coupon.ui.dto.CouponProductResponseDto;
import kkakka.mainservice.coupon.ui.dto.CouponRequestDto;
import kkakka.mainservice.coupon.ui.dto.CouponResponseDto;
import kkakka.mainservice.member.member.domain.Member;
import kkakka.mainservice.member.member.domain.repository.MemberRepository;
import kkakka.mainservice.product.domain.Product;
import kkakka.mainservice.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    private final SortWithMaximumDiscountAmount sortWithMaximumDiscountAmount;

    /* 관리자 쿠폰 등록 */
    @Transactional
    public Long createCoupon(CouponRequestDto couponRequestDto) {
        if (couponRequestDto.isValidDate()) {
            if (PriceRule.GRADE_COUPON.equals(couponRequestDto.getPriceRule())) {
                Coupon coupon = couponRepository.save(toCouponEntity(couponRequestDto));
                List<Member> members = memberRepository.findByGrade(
                    couponRequestDto.getGrade());
                for (Member member : members) {
                    MemberCoupon memberCoupon = MemberCoupon.create(member, coupon);
                    memberCouponRepository.save(memberCoupon);
                }
                return coupon.getId();
            }

            if (PriceRule.COUPON.equals(couponRequestDto.getPriceRule())) {
                Category category =
                    couponRequestDto.getCategoryId() == null ? null : getCategory(couponRequestDto);
                Product product =
                    couponRequestDto.getProductId() == null ? null : getProduct(couponRequestDto);
                Coupon coupon = couponRepository.save(
                    toCouponEntity(couponRequestDto, category, product));
                return coupon.getId();
            }
        }
        throw new InvalidCouponRequestException();
    }

    private Coupon toCouponEntity(CouponRequestDto couponRequestDto) {
        if (couponRequestDto.getPercentage() != null) {
            return Coupon.create(
                couponRequestDto.getGrade(),
                couponRequestDto.getName(),
                PriceRule.GRADE_COUPON,
                couponRequestDto.getStartedAt(),
                couponRequestDto.getExpiredAt(),
                couponRequestDto.getPercentage(),
                couponRequestDto.getMaxDiscount(),
                couponRequestDto.getMinOrderPrice());
        }
        return Coupon.create(
            couponRequestDto.getGrade(),
            couponRequestDto.getName(),
            PriceRule.GRADE_COUPON,
            couponRequestDto.getStartedAt(),
            couponRequestDto.getExpiredAt(),
            couponRequestDto.getMaxDiscount(),
            couponRequestDto.getMinOrderPrice());
    }

    private Coupon toCouponEntity(CouponRequestDto couponRequestDto, Category category,
        Product product) {
        if (couponRequestDto.getPercentage() != null) {
            return Coupon.create(
                category,
                product,
                couponRequestDto.getName(),
                PriceRule.COUPON,
                couponRequestDto.getStartedAt(),
                couponRequestDto.getExpiredAt(),
                couponRequestDto.getPercentage(),
                couponRequestDto.getMaxDiscount(),
                couponRequestDto.getMinOrderPrice()
            );
        }
        return Coupon.create(
            category,
            product,
            couponRequestDto.getName(),
            PriceRule.COUPON,
            couponRequestDto.getStartedAt(),
            couponRequestDto.getExpiredAt(),
            couponRequestDto.getMaxDiscount(),
            couponRequestDto.getMinOrderPrice()
        );
    }

    private Product getProduct(CouponRequestDto couponRequestDto) {
        return productRepository.findById(couponRequestDto.getProductId())
            .orElseThrow(IllegalArgumentException::new);
    }

    private Category getCategory(CouponRequestDto couponRequestDto) {
        return categoryRepository.findById(couponRequestDto.getCategoryId())
            .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void deleteCouponByCouponId(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(KkaKkaException::new);
        coupon.deleteCoupon();
        couponRepository.save(coupon);
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllMemberCouponByCouponId(
            couponId);
        if (!memberCoupons.isEmpty()) {
            for (MemberCoupon memberCoupon : memberCoupons) {
                memberCouponRepository.delete(memberCoupon);
            }
        }
    }

    public List<CouponResponseDto> showAllCouponsNotDeleted() {
        List<Coupon> coupons = couponRepository.findAllCouponsNotDeleted();
        return coupons.stream()
            .map(CouponResponseDto::create)
            .collect(Collectors.toList());
    }

    /* 사용자 쿠폰 다운로드 */
    @Transactional
    public void downloadCoupon(Long couponId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(IllegalArgumentException::new);
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(IllegalArgumentException::new);
        MemberCoupon memberCoupon = MemberCoupon.create(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    /* 회원 쿠폰 사용 */
    @Transactional(propagation = Propagation.REQUIRED)
    public void useCouponByMember(Long couponId, Long memberId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(KkaKkaException::new);
        if (coupon.isNotExpired()) {
            MemberCoupon memberCoupon = memberCouponRepository.findMemberCouponByCouponIdAndMemberId(
                couponId, memberId);
            memberCoupon.useCoupon();
            memberCouponRepository.save(memberCoupon);
        }
    }

    /* 사용자 사용 가능한 쿠폰 목록 조회 */
    public List<Coupon> findUsableCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository
            .findAllByMemberIdAndIsUsedFalse(memberId);
        return memberCoupons.stream()
            .map(memberCoupon -> memberCoupon.getCoupon())
            .collect(Collectors.toList());
    }

    /* 사용자 다운 가능한 쿠폰 목록 조회 */
    public List<CouponResponseDto> findDownloadableCoupons(Long memberId) {
        List<Coupon> coupons = couponRepository.findAll();
        List<Coupon> downloadedCoupons = memberCouponRepository.findAllByMemberId(
            memberId);
        coupons.removeAll(downloadedCoupons);
        return coupons.stream()
            .filter(coupon -> isDownloadable(coupon))
            .map(coupon -> CouponResponseDto.create(coupon))
            .collect(Collectors.toList());
    }

    private boolean isDownloadable(Coupon coupon) {
        return coupon.isNotExpired() && coupon.getPriceRule().equals(PriceRule.COUPON)
            && !coupon.isDeleted();
    }

    /* 상품에 대해 적용 가능한 쿠폰 조회 */
    public List<CouponProductResponseDto> showCouponsByProductIdAndMemberId(Long productId,
        Long memberId) {

        Product product = productRepository.findById(productId).orElseThrow(KkaKkaException::new);
        List<Coupon> coupons = findProductCouponsByProductId(productId);

        if (product.getCategory() != null) {
            coupons.addAll(findCategoryCouponsByCategoryId(productId));
        }

        int productPrice = product.getPrice() - product.getDiscount();
        List<CouponProductResponseDto> couponProductResponseDtos = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (isDownloadableCoupon(coupon.getId(), memberId)) {
                couponProductResponseDtos.add(
                    CouponProductResponseDto.create(coupon, true, productPrice));
                continue;
            }
            couponProductResponseDtos.add(
                CouponProductResponseDto.create(coupon, false, productPrice));
        }

        List<MemberCoupon> memberCoupons = memberCouponRepository.findGradeCouponByMemberId(
            memberId);
        if (!memberCoupons.isEmpty()) {
            for (MemberCoupon memberCoupon : memberCoupons) {
                couponProductResponseDtos.add(
                    CouponProductResponseDto.create(memberCoupon.getCoupon(), false, productPrice));
            }
        }

        return sortWithMaximumDiscountAmount.sort(couponProductResponseDtos);
    }

    private boolean isDownloadableCoupon(Long couponId, Long memberId) {
        Long count = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
        return count == 0;
    }

    private List<Coupon> findCategoryCouponsByCategoryId(Long productId) {
        return couponRepository.findCouponsByCategoryIdAndNotDeleted(productId);
    }

    private List<Coupon> findProductCouponsByProductId(Long productId) {
        return couponRepository.findCouponsByProductIdAndNotDeleted(productId);
    }

    public List<CouponProductResponseDto> showCouponsByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(KkaKkaException::new);
        List<Coupon> coupons = couponRepository.findCouponsByProductIdAndNotDeleted(productId);
        List<CouponProductResponseDto> couponProductResponseDtos = coupons.stream()
            .map(coupon -> CouponProductResponseDto.create(coupon, true,
                product.getPrice() - product.getDiscount()))
            .collect(Collectors.toList());

        return sortWithMaximumDiscountAmount.sort(couponProductResponseDtos);

    }

    public int showMemberCouponCount(Long memberId) {
        return memberCouponRepository.countAllByMemberIdAndIsUsedFalse(memberId);
    }
}