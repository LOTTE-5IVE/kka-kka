package kkakka.mainservice.coupon.domain;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class MemberCouponId implements Serializable {

  private Long memberId;
  private Long couponId;

}
