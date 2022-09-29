package kkakka.mainservice.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Getter
@Table(name = "MemberCoupon")
public class MemberCoupon {

    @EmbeddedId
    private MemberCouponId memberCouponId;
}
