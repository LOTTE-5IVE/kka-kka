package kkakka.mainservice.coupon.domain;

import java.io.Serializable;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MemberCouponId that = (MemberCouponId) o;
    return Objects.equals(memberId, that.memberId) && Objects.equals(couponId,
        that.couponId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberId, couponId);
  }
}
