package kkakka.mainservice.coupon.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kkakka.mainservice.member.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MemberCoupon")
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    private Boolean isUsed;
    private Boolean isApply;

    public static MemberCoupon create(Member member, Coupon coupon) {
        return new MemberCoupon(null, member, coupon, false , false);
    }

    public void useCoupon() {
        this.isUsed = true;
    }

    public void cancelCoupon() {
        this.isApply = false;
    }

    public void applyCoupon() {
        this.isApply = true;
    }

    public Boolean isUsable() {
        return !this.isApply;
    }

    public boolean isUsed() {
        return this.isUsed;
    }
}
