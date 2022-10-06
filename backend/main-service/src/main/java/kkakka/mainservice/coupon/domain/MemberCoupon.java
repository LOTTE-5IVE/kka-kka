package kkakka.mainservice.coupon.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MemberCoupon")
public class MemberCoupon {

    @EmbeddedId
    private MemberCouponId memberCouponId;
}
