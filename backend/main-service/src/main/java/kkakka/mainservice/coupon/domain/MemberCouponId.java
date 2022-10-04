package kkakka.mainservice.coupon.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberCouponId implements Serializable {

  private Long memberId;
  private Long couponId;
}
