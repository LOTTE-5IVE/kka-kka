package kkakka.mainservice.coupon.domain;

import kkakka.mainservice.member.domain.Member;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter @Embeddable
public class MemberCouponId implements Serializable {

    private Long memberId;
    private Long couponId;
    
}
